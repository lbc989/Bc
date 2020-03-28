

public class Demo2Servlet extends HttpServlet {
    public Demo2Servlet() {
        super();
        System.out.println("Demo2Servlet的构造方法");
    }

    public void doGet(Request req, Response resp) {
        System.out.println("demo2的doGet方法执行中。。。。。。。。。。。。。");
    }

    public void doPost(Request req, Response resp)  {
        System.out.println("demo2的doPost方法执行中。。。。。。。。。。。。。");
    }
    public void service(Request arg0, Response arg1) throws Exception {
        System.out.println("service被调用了...");
        super.service(arg0, arg1);
    }
    public void init() throws Exception {
        System.out.println("demo2的init方法");
    }



    @Override
    public void destory() throws Exception {

    }
}

