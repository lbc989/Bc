package com.lagou.edu.MyAnnotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class MyReflect {
    //传入对象
    public static boolean setObjByFieldAnno(Object o) {
        //获取Class
        Class c = o.getClass();
        /*获得域*/
        Field[] fields = c.getDeclaredFields();
        //返回值用于判断是否完成这个方法
        boolean b = false;
        //遍历域
        for (Field f : fields) {
            //获取域中的注解，遍历注解
            Annotation[] anns = f.getAnnotations();
            for (Annotation ann : anns) {
                //这里使用instanceof关键字，判断注解中是否包含MyAutowired
                if (ann instanceof MyAutowired) {
                    System.out.println(f.getName() + "--这个域使用了我的注解");
                    //f.getGenericType()：获取该域的类型
                    System.out.println(f.getGenericType().toString() + "--这个域的类型");
                    //转成Class
                    Class c2 = (Class) f.getGenericType();
                    try {//创建实例
                        Object o2 = c2.newInstance();
                        //这个方法是将实例注入到这个域中，详细使用请查阅JAVA API
                        f.set(o, o2);
                        System.out.println("成功注入");
                        //成功则返回true
                        b = true;
                        return b;
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                }

            }
        }
        return b;
    }
}
