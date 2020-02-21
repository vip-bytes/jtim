package cn.bytes.jtim.http.server;

import cn.bytes.jtim.core.config.Configuration;
import cn.bytes.jtim.core.module.connection.SimpleConnectionModule;
import cn.bytes.jtim.core.module.handler.SimpleChannelHandlerProtoBufModule;
import cn.bytes.jtim.core.module.initialize.SimpleServerInitializeModule;
import cn.bytes.jtim.core.module.retry.RetryModule;
import cn.bytes.jtim.core.module.retry.SimpleRetryModule;
import cn.bytes.jtim.core.module.route.RouteModule;
import cn.bytes.jtim.core.module.route.SimpleRedisRouteModule;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import static cn.bytes.jtim.common.constant.DefineConstant.BYTES_PUSH_SERVER_TCP;

/**
 * @version 1.0
 * @date 2020/2/20 14:18
 */
public class ModuleHostTest {

    public static void main(String[] args) {

        Configuration configuration = new Configuration();
        configuration.setHost("127.0.0.1");
        configuration.setPort(1999);
        SimpleServerInitializeModule nettyTcpServer = new SimpleServerInitializeModule(configuration);

        SimpleChannelHandlerProtoBufModule simpleChannelHandlerProtoBufModule = new SimpleChannelHandlerProtoBufModule();

        RetryModule retryModule = SimpleRetryModule.builder().loop(true).build();

        nettyTcpServer
                .then(retryModule)
                .then(simpleChannelHandlerProtoBufModule.codec(new ProtobufServerHandler()))
                .then(new SimpleConnectionModule())
                .then(SimpleRetryModule.builder().build());
        //.then(getClusterModule().then(SimpleRetryModule.builder().build()));

        System.out.println(simpleChannelHandlerProtoBufModule.getHost());

        System.out.println(nettyTcpServer.getModule(RetryModule.class));

    }

    public static RouteModule getClusterModule() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://192.168.50.110:6379");

        RedissonClient redisClient = Redisson.create(config);
        SimpleRedisRouteModule simpleRedisClusterModule =
                new SimpleRedisRouteModule(BYTES_PUSH_SERVER_TCP, redisClient);

        return simpleRedisClusterModule;
    }
}
