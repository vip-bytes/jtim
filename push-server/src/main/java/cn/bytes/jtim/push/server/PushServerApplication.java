package cn.bytes.jtim.push.server;

import cn.bytes.jtim.push.server.context.NettyTcpServerContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * websocket + rpc
 * websocket + tcp
 * 客户端连接 + 内部通信服务
 *
 * @version 1.0
 * @date 2020/2/21 12:12
 */
@SpringBootApplication
@Import({NettyTcpServerContext.class})
public class PushServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PushServerApplication.class);
    }

}
