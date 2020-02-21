package cn.bytes.jtim.http.server;

import cn.bytes.jtim.http.server.context.HttpServerContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * 外部接入服务
 *
 * @version 1.0
 * @date 2020/2/21 12:09
 */
@SpringBootApplication
@Import({HttpServerContext.class})
public class HttpServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HttpServerApplication.class);
    }

}
