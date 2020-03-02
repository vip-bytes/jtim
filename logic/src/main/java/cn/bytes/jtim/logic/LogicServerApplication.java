package cn.bytes.jtim.logic;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.dubbo.config.spring.context.annotation.EnableDubboConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @version 1.0
 * @date 2020/2/24 17:00
 */
@SpringBootApplication
//@EnableAutoConfiguration
public class LogicServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogicServerApplication.class,args);
    }

}
