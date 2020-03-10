package com.lbc.mvcframework.servlet;

import com.lbc.mvcframework.annotation.*;
import com.lbc.mvcframework.pojo.Handler;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DispatcherServlet extends HttpServlet {

    private Properties properties = new Properties();
    private List<String> classNames = new ArrayList<>();
    //ioc容器
    private Map<String,Object> ioc = new HashMap<>();
    //HandlerMapping
    // private Map<String,Method> handlerMapping = new HashMap<>();//存储url和method之间的映射关系
    private List<Handler> handlerMapping = new  ArrayList<>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        //加载配置文件 springmvc.properties
        String contextConfigLocation = config.getInitParameter("contextConfigLocation");

        doLoadConfig(contextConfigLocation);
        //扫描相关的类和注解

        doScan(properties.getProperty("scanPackage"));
        //初始化bean对象（基于注解的ioc容器）

        doInstance();
        //实现依赖注入

        doAutoWired();
        //构造一个HandlerMapping，将配置好的url和Method建立映射关系
        initHandlerMapping();



        System.out.println("mvc初始化完成。。。。。。");
        //等待请求进入，处理请求
    }

    private boolean authority() {
        if(ioc.isEmpty())return false;
        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            //获取ioc中对象的class类型
            Class<?> aClass = entry.getValue().getClass();
            Method[] methods = aClass.getMethods();
            for (int i = 0; i < methods.length; i++) {
                Method method = methods[i];

                if(!method.isAnnotationPresent(Security.class)){continue;}
                Security annotation = method.getAnnotation(Security.class);
                String name = annotation.value();//username
                if(name.equalsIgnoreCase("zhangsan")){
                    return true;
                }


    }}
    return false;}


    //加载配置文件
    private void doLoadConfig(String contextConfigLocation) {
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(contextConfigLocation);
        try {
            properties.load(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //扫描类
    //scanPackage:com.lbc.demo pacaket->找到磁盘上的文件夹(File) com.lbc.demo
    private void doScan(String scanPackage) {
        String scanPackagePath = Thread.currentThread().getContextClassLoader().getResource("").getPath() + scanPackage.replaceAll("\\.", "/");
        File pack = new File(scanPackagePath);
        File[] files = pack.listFiles();
        for (File file : files) {
            if(file.isDirectory()){
                doScan(scanPackage + "." + file.getName());//com.lbc.demo.controller
            }else if(file.getName().endsWith(".class")){
                String className = scanPackage + "."  + file.getName().replaceAll(".class", "");
                classNames.add(className);
            }

        }

    }

    //ioc容器
    private void doInstance()  {
        if(classNames.size() == 0) return;

        try{
            for (int i = 0; i < classNames.size(); i++) {
                String className =  classNames.get(i);  // com.lbc.demo.controller.DemoController

                // 反射
                Class<?> aClass = Class.forName(className);
                // 区分controller和service
                if(aClass.isAnnotationPresent(MyController.class)) {
                    // controller的id此处不做过多处理，不取value了，就拿类的首字母小写作为id，保存到ioc中
                    String simpleName = aClass.getSimpleName();// DemoController
                    String lowerFirstSimpleName = lowerFirst(simpleName); // demoController
                    Object o = aClass.newInstance();
                    ioc.put(lowerFirstSimpleName,o);
                }else if(aClass.isAnnotationPresent(MyService.class)) {
                    MyService annotation = aClass.getAnnotation(MyService.class);
                    //获取注解value值
                    String beanName = annotation.value();

                    // 如果指定了id，就以指定的为准
                    if(!"".equals(beanName.trim())) {
                        ioc.put(beanName,aClass.newInstance());
                    }else{
                        // 如果没有指定，就以类名首字母小写
                        beanName = lowerFirst(aClass.getSimpleName());
                        ioc.put(beanName,aClass.newInstance());
                    }


                    // service层往往是有接口的，面向接口开发，此时再以接口名为id，放入一份对象到ioc中，便于后期根据接口类型注入
                    Class<?>[] interfaces = aClass.getInterfaces();
                    for (int j = 0; j < interfaces.length; j++) {
                        Class<?> anInterface = interfaces[j];
                        // 以接口的全限定类名作为id放入
                        ioc.put(anInterface.getName(),aClass.newInstance());
                    }
                }else{
                    continue;
                }

            }}
        catch (Exception e) {
            e.printStackTrace();
        }
        }


    public String lowerFirst(String str){
        char[] chars = str.toCharArray();
        if('A'<=chars[0] && chars[0]<='Z'){
            chars[0] += 32;
        }
        return String.valueOf(chars);

    }

    //实现依赖注入
    private void doAutoWired() {
        if(ioc.isEmpty())return;
        //遍历ioc中所有对象，看其中的字段上有无@MyAutoWired注解，有则注入依赖关系
        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            //获取bean对象中的字段信息
            Field[] declaredFields = entry.getValue().getClass().getDeclaredFields();
            for (int i = 0; i < declaredFields.length; i++) {
                Field declaredField = declaredFields[i];
                if(!declaredField.isAnnotationPresent(MyAutoWired.class)){continue;}
                //有该注解
                MyAutoWired annotation = declaredField.getAnnotation(MyAutoWired.class);
                String beanName = annotation.value();//需要注入的bean的id
                if("".equals(beanName.trim())){
                    //需要根据当前字段的类型注入（接口注入） IDemoService
                    beanName = declaredField.getType().getName();
                }
                //开始赋值
                declaredField.setAccessible(true);
                try {
                    declaredField.set(entry.getValue(),ioc.get(beanName));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }


            }



        }
    }

    //构造一个HandlerMapping
    private void initHandlerMapping() {
        if(ioc.isEmpty())return;
        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            //获取ioc中对象的class类型
            Class<?> aClass = entry.getValue().getClass();
            if(!aClass.isAnnotationPresent(MyController.class)){continue;}
            String baseUrl = "";
            if(aClass.isAnnotationPresent(MyRequestMapping.class)){
                MyRequestMapping annotation = aClass.getAnnotation(MyRequestMapping.class);
                baseUrl = annotation.value();//demo

            }
            Method[] methods = aClass.getMethods();
            for (int i = 0; i < methods.length; i++) {
                Method method = methods[i];

                if(!method.isAnnotationPresent(MyRequestMapping.class)){continue;}
                MyRequestMapping annotation = method.getAnnotation(MyRequestMapping.class);
                String methodUrl = annotation.value();//query
                String url = baseUrl + methodUrl;

                //把method信息和url封装为一个handler
                Handler handler = new Handler(entry.getValue(),method, Pattern.compile(url));

                //计算方法的参数位置信息
                // query(String name, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
                Parameter[] parameters = method.getParameters();
                for (int j = 0; j < parameters.length; j++) {
                    Parameter parameter = parameters[j];
                    if(parameter.getType() == HttpServletRequest.class || parameter.getType() == HttpServletResponse.class) {
                        // 如果是request和response对象，那么参数名称写HttpServletRequest和HttpServletResponse
                        handler.getParamIndexMapping().put(parameter.getType().getSimpleName(),j);
                    }else{
                        handler.getParamIndexMapping().put(parameter.getName(),j);  // <name,2>
                    }
                }

                //建立url和method之间的映射关系，缓存到map中
                handlerMapping.add(handler);
            }

        }
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name[] = new String[10];
        // 处理请求
        String requestURI = req.getRequestURI();
        if(ioc.isEmpty())return ;
        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            //获取ioc中对象的class类型
            Class<?> aClass = entry.getValue().getClass();
            Method[] methods = aClass.getMethods();
            for (int i = 0; i < methods.length; i++) {
                Method method = methods[i];

                if (!method.isAnnotationPresent(Security.class)) {
                    continue;
                }
                String name1 = method.getName();
                if(name1.equalsIgnoreCase("query")){
                    Security annotation = method.getAnnotation(Security.class);
                    name[0] = annotation.value();//username
                }else{
                    Security annotation = method.getAnnotation(Security.class);
                    name[1] = annotation.value();//username
                }


            }}
        // Method method = handlerMapping.get(requestURI);
        // method.invoke();

        // if(requestURI.contains(name)){
            // 根据uri获取到能够处理当前请求的hanlder（从handlermapping中（list））
            Handler handler = getHandler(req);

            if(handler == null) {
                resp.getWriter().write("404 not found");
                return;
            }

            // 参数绑定
            // 获取所有参数类型数组，这个数组的长度就是我们最后要传入的args数组的长度
            Class<?>[] parameterTypes = handler.getMethod().getParameterTypes();


            // 根据上述数组长度创建一个新的数组（参数数组，是要传入反射调用的）
            Object[] paraValues = new Object[parameterTypes.length];

            // 以下就是为了向参数数组中塞值，而且还得保证参数的顺序和方法中形参顺序一致

            Map<String, String[]> parameterMap = req.getParameterMap();

            // 遍历request中所有参数  （填充除了request，response之外的参数）
            for(Map.Entry<String,String[]> param: parameterMap.entrySet()) {
                // name=1&name=2   name [1,2]

                String value = StringUtils.join(param.getValue(), ",");  // 如同 1,2

                if((value.equalsIgnoreCase(name[1])&&requestURI.equalsIgnoreCase("/demo/query2"))||(value.equalsIgnoreCase(name[0])&&requestURI.equalsIgnoreCase("/demo/query"))){
                   // 如果参数和方法中的参数匹配上了，填充数据
                   if(!handler.getParamIndexMapping().containsKey(param.getKey())) {continue;}

                   // 方法形参确实有该参数，找到它的索引位置，对应的把参数值放入paraValues
                   Integer index = handler.getParamIndexMapping().get(param.getKey());//name在第 2 个位置

                   paraValues[index] = value;  // 把前台传递过来的参数值填充到对应的位置去

                   int requestIndex = handler.getParamIndexMapping().get(HttpServletRequest.class.getSimpleName()); // 0
                   paraValues[requestIndex] = req;


                   int responseIndex = handler.getParamIndexMapping().get(HttpServletResponse.class.getSimpleName()); // 1
                   paraValues[responseIndex] = resp;




                   // 最终调用handler的method属性
                   try {
                       handler.getMethod().invoke(handler.getController(),paraValues);
                   }

                   catch (IllegalAccessException e) {
                       e.printStackTrace();
                   } catch (InvocationTargetException e) {
                       e.printStackTrace();
                   }
               }else {
                   resp.getWriter().write("No authority!!!");
                   return;
               }
               }

        // }


    }

    private Handler getHandler(HttpServletRequest req) {
        if(handlerMapping.isEmpty()){return null;}

        String url = req.getRequestURI();

        for(Handler handler: handlerMapping) {
            Matcher matcher = handler.getPattern().matcher(url);
            if(!matcher.matches()){continue;}
            return handler;
        }

        return null;

    }


}
