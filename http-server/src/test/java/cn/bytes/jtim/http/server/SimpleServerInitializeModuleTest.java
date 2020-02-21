package cn.bytes.jtim.http.server;

import cn.bytes.jtim.core.config.Configuration;
import cn.bytes.jtim.core.module.connection.SimpleConnectionModule;
import cn.bytes.jtim.core.module.handler.SimpleChannelHandlerProtoBufModule;
import cn.bytes.jtim.core.module.initialize.SimpleServerInitializeModule;
import cn.bytes.jtim.core.module.retry.SimpleRetryModule;
import cn.bytes.jtim.core.module.route.RouteModule;
import cn.bytes.jtim.core.module.route.SimpleRedisRouteModule;
import cn.bytes.jtim.core.module.route.selector.RouteKey;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import static cn.bytes.jtim.common.constant.DefineConstant.BYTES_PUSH_SERVER_TCP;

/**
 * @version 1.0
 * @date 2020/2/13 22:16
 */
public class SimpleServerInitializeModuleTest {

    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.setHost("127.0.0.1");
        configuration.setPort(1998);

        SimpleServerInitializeModule serverInitializeModule = new SimpleServerInitializeModule(configuration);
        serverInitializeModule
                .then(SimpleRetryModule.builder().delay(5).loop(true).build())
                .then(new SimpleChannelHandlerProtoBufModule()
                        .codec(new ProtobufServerHandler()))
                .then(new SimpleConnectionModule())
                .then(getClusterModule(configuration));

        serverInitializeModule.open();
    }

    public static RouteModule getClusterModule(Configuration configuration) {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://192.168.50.110:6379");

        RedissonClient redisClient = Redisson.create(config);
        SimpleRedisRouteModule simpleRedisClusterModule =
                new SimpleRedisRouteModule(BYTES_PUSH_SERVER_TCP, redisClient);

        simpleRedisClusterModule.route(RouteKey.builder()
                .bind(true)
                .id("tcp-001")
                .host(configuration.getHost())
                .port(configuration.getPort())
                .build());

        return simpleRedisClusterModule;
    }
}
