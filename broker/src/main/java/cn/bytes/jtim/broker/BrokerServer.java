package cn.bytes.jtim.broker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @version 1.0
 * @date 2020/2/12 19:57
 */
@SpringBootApplication
@Import(InitializingServer.class)
public class BrokerServer {

    public static void main(String[] args) {
        SpringApplication.run(BrokerServer.class, args);
    }

}
