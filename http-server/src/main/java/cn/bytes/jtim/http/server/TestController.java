package cn.bytes.jtim.http.server;

import cn.bytes.jtim.core.module.route.RouteModule;
import cn.bytes.jtim.core.module.route.selector.RouteKey;
import cn.bytes.jtim.core.protocol.protobuf.Message;
import cn.bytes.jtim.core.protocol.protobuf.O2MDistributeRequest;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 * @date 2020/2/21 18:35
 */
@Component
public class TestController {

    private RouteModule routeModule;

    public TestController(RouteModule routeModule) {
        this.routeModule = routeModule;
    }

    @PostConstruct
    public void sendTest() throws InterruptedException {

        while (true) {

            int port = System.currentTimeMillis() % 2 == 0 ? 2020 : 2021;

            System.out.println("分发消息: " + port);
            routeModule.route(
                    RouteKey.builder().host("192.168.0.102").port(port).build())
                    .distribute(Message.newBuilder()
                            .setCmd(Message.Cmd.O2MDistributeRequest)
                            .setO2MDistributeRequest(O2MDistributeRequest.newBuilder()
                                    .setContent("测试消息分发")
                                    .build())
                            .build());

            TimeUnit.SECONDS.sleep(5);
        }
    }

}
