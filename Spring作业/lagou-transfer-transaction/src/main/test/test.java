import com.lagou.edu.MyAnnotation.MyAutowired;
import com.lagou.edu.MyAnnotation.MyService;
import com.lagou.edu.MyAnnotation.MyTransactional;
import com.lagou.edu.service.TransferService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

@EnableAspectJAutoProxy(proxyTargetClass = true)
public class test {
    public static void main(String[] args) throws Exception {
        ApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        TransferService transferService = (TransferService) classPathXmlApplicationContext.getBean("transferService");
        transferService.transfer("6029621011000","6029621011001",100);
    }
}
