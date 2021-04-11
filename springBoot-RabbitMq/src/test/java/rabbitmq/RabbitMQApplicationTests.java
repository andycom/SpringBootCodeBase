package rabbitmq;

import com.fancv.RabbitMQApplication;
import com.fancv.rabbit.hello.HelloSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RabbitMQApplication.class)
public class RabbitMQApplicationTests {

    @Test
    public void contextLoads() {
        System.out.println("hello world");
    }

    @Autowired(required = false)
    private HelloSender helloSender;

    @Test
    public void hello() throws Exception {
        helloSender.send("sadfas");
    }

}
