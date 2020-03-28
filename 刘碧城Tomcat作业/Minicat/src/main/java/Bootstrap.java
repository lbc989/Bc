import org.dom4j.*;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.*;

public class Bootstrap {
    private int port = 8080;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void start() throws Exception {

        // 加载解析相关的配置，web.xml
        loadServlet();
        // 定义一个线程池
        int corePoolSize = 10;
        int maximumPoolSize = 50;
        long keepAlveTime = 100L;
        TimeUnit unit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(50);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAlveTime,
                unit,
                workQueue,
                threadFactory,
                handler
        );



        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Minicat Start............port:"+port);
        //1.0 请求localhost:8080  返回固定字符串到页面"Helle,Minicat!"
        // while(true){
        //     Socket socket = serverSocket.accept();
        //     OutputStream outputStream = socket.getOutputStream();
        //     String data = "Helle,Minicat!";
        //     String responseText = HttpProtocolUtil.getHttpHeader200(data.getBytes().length) + data;
        //     outputStream.write(responseText.getBytes());
        //     outputStream.close();
        // }

        //2.0 封装Request对象和Response对象，返回html静态资源文件
        // while(true){
        //     Socket socket = serverSocket.accept();
        //     InputStream inputStream = socket.getInputStream();
        //     Request request = new Request(inputStream);
        //     Response response = new Response(socket.getOutputStream());
        //     response.outputHtml(request.getUrl());
        //
        //     socket.close();
        // }

        //3.0 请求动态资源 Servlet
        // while(true){
        //     Socket socket = serverSocket.accept();
        //     InputStream inputStream = socket.getInputStream();
        //     Request request = new Request(inputStream);
        //     Response response = new Response(socket.getOutputStream());
        //     // 静态资源处理
        //     if(servletMap.get(request.getUrl())==null){
        //         response.outputHtml(request.getUrl());
        //     }else {
        //         HttpServlet httpServlet = servletMap.get(request.getUrl());
        //         httpServlet.service(request,response);
        //     }
        //     socket.close();

        //多线程改造（不使用线程池）
        // while(true){
        //     Socket socket = serverSocket.accept();
        //     RequestProcessor requestProcessor = new RequestProcessor(socket,servletMap);
        //     requestProcessor.start();
        //
        // }

        //多线程改造（使用线程池）
        while(true){
            Socket socket = serverSocket.accept();
            RequestProcessor requestProcessor = new RequestProcessor(socket,servletMap,path);
            //requestProcessor.start();
            threadPoolExecutor.execute(requestProcessor);


        }


    }

    private Map<String, HttpServlet> servletMap = new HashMap<>();
    String path = null;

    private void loadServlet() {

        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("server.xml");
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(resourceAsStream);
            Element rootElement = document.getRootElement();
            List<Element> selectNodes = rootElement.selectNodes("//Service");
            for (int i = 0; i < selectNodes.size(); i++) {
                Element element = selectNodes.get(i);
                // <servlet-name>lagou</servlet-name>
                Element servletnameElement = (Element) element.selectSingleNode("Engine");
                Element hostnameElement = (Element) servletnameElement.selectSingleNode("Host");
                Attribute appBase = hostnameElement.attribute("appBase");
                path = appBase.getValue();

                // Element servletclassElement = (Element) element.selectSingleNode("servlet-class");
                // String servletClass = servletclassElement.getStringValue();

                // 根据servlet-name的值找到url-pattern
                // Element servletMapping = (Element) rootElement.selectSingleNode("/web-app/servlet-mapping[servlet-name='" +servletName +"']");
                // /lagou
                // String urlPattern = servletMapping.selectSingleNode("url-pattern").getStringValue();
                // servletMap.put(urlPattern, (HttpServlet) Class.forName(servletClass).newInstance());



            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        // catch (IllegalAccessException e) {
        //     e.printStackTrace();
        // } catch (InstantiationException e) {
        //     e.printStackTrace();
        // } catch (ClassNotFoundException e) {
        //     e.printStackTrace();
        // }


    }

    public static void main(String[] args) throws IOException {

        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }







}
