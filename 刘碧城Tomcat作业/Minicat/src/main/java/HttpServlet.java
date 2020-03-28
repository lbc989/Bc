

public abstract class HttpServlet implements Servlet {

    @Override
    public void init() throws Exception {

    }

    @Override
    public void destory() throws Exception {

    }

    protected abstract void doGet(Request request, Response response);{}

    protected abstract void doPost(Request request, Response response);{}

    public void service(Request request, Response response) throws Exception {
        if("GET".equalsIgnoreCase(request.getMethod())) {
            doGet(request,response);
        }else{
            doPost(request,response);
        }
    }
}
