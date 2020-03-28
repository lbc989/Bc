import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.Socket;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;

public class RequestProcessor extends Thread {
    private Socket socket;
    private Map<String, HttpServlet> servletMap;
    String path;

    public RequestProcessor(Socket socket, Map<String, HttpServlet> servletMap) {
        this.socket = socket;
        this.servletMap = servletMap;
    }

    public RequestProcessor(Socket socket, Map<String, HttpServlet> servletMap,String path) {
        this.socket = socket;
        this.servletMap = servletMap;
        this.path = path;
    }

    @Override
    public void run() {
        try{
            InputStream inputStream = socket.getInputStream();
            // 封装Request对象和Response对象
            Request request = new Request(inputStream);
            Response response = new Response(socket.getOutputStream());

            // 静态资源处理
            // if(servletMap.get(request.getUrl()) == null) {
            //     response.outputHtml(request.getUrl());
            // }
            // else{
                // 动态资源servlet请求
                // String appBasepath = "E:/project/Minicat/";
                String requestUrl = request.getUrl();
                // String projectName=appBasepath + "/" + requestUrl.substring(    requestUrl.indexOf("/")+1  , requestUrl.lastIndexOf("/"));
                String servletName=requestUrl.substring(    requestUrl.lastIndexOf("/")+1  , requestUrl.length());
            //2. 动态字节码加载     到  res/找servlet.class文件
            //    URLClassLoader
                URL[] urls=new URL[ 1  ];

                urls[0]=new URL("file", null,  path    );  // ??
                URLClassLoader ucl=  new URLClassLoader(  urls)     ;
                // 3. URL地址  =>   file:\\\
                //4.  Class urlclassloader.loadClass(  类的名字  );
                Class c=ucl.loadClass(  servletName );
                //5.   以反射的形式  newInstance()创建  servlet实例.
                Servlet servlet=(Servlet) c.newInstance();   //  -> 调用 构造方法
                // 6. 再以生命周期的方式 来调用servlet中的各方法
                if(   servlet!=null&&   servlet instanceof Servlet){
                    //生命周期方法的调用
                    servlet.init();
                    //父类引用只能调用子类重写了父类的方法而不能调用子类所特有的方法
                    ((HttpServlet)servlet).service( request, response);
                }

            } catch (Exception e) {
            e.printStackTrace();

            }

        }
    }

