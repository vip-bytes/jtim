package cn.bytes.jtim.broker.test;

import cn.bytes.jtim.core.config.Configuration;
import cn.bytes.jtim.core.module.cluster.ClusterModule;
import cn.bytes.jtim.core.module.cluster.ClusterServerContent;
import cn.bytes.jtim.core.module.cluster.SimpleRedisClusterModule;
import cn.bytes.jtim.core.module.connection.SimpleConnectionModule;
import cn.bytes.jtim.core.module.handler.SimpleChannelHandlerProtoBufModule;
import cn.bytes.jtim.core.module.initialize.SimpleServerInitializeModule;
import cn.bytes.jtim.core.module.retry.SimpleRetryModule;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import static cn.bytes.jtim.common.constant.DefineConstant.NETTY_TCP_SERVER_CLUSTER_KEY;

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

    public static ClusterModule getClusterModule(Configuration configuration) {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://192.168.50.110:6379");

        RedissonClient redisClient = Redisson.create(config);
        SimpleRedisClusterModule simpleRedisClusterModule =
                new SimpleRedisClusterModule(NETTY_TCP_SERVER_CLUSTER_KEY, redisClient);

        simpleRedisClusterModule.content(ClusterServerContent.builder()
                .host(configuration.getHost())
                .port(configuration.getPort())
                .tag("tcp")
                .id("tcp-001")
                .build());

        return simpleRedisClusterModule;
    }
}
