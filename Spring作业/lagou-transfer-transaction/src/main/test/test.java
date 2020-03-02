import com.lagou.edu.service.TransferService;
import org.springframework.context.support.ClassPathXmlApplicationContext;
public class test {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        TransferService transferService = (TransferService) classPathXmlApplicationContext.getBean("transferService");
        transferService.transfer("6029621011000","6029621011001",100);
    }
}
