import javax.net.ssl.SSLSession;
import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

public class Response  {
    private OutputStream outputStream;

    public Response(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public Response() {
    }

    public void output(String concent) throws IOException {
        outputStream.write(concent.getBytes());
    }
    public void outputHtml(String path) throws IOException {
        String absresourcePath = StaticResourceUtil.getAbsPath(path);
        File file = new File(absresourcePath);
        if(file.exists() && file.isFile()){
            StaticResourceUtil.outputStaticResource(new FileInputStream(file),outputStream);

        }else {
            output(HttpProtocolUtil.getHttpHeader404());

        }

    }

}
