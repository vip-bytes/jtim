package cn.bytes.jtim.broker.test;

import cn.bytes.jtim.core.config.Configuration;
import cn.bytes.jtim.core.module.cluster.ClusterModule;
import cn.bytes.jtim.core.module.cluster.SimpleRedisClusterModule;
import cn.bytes.jtim.core.module.connection.SimpleConnectionModule;
import cn.bytes.jtim.core.module.handler.SimpleChannelHandlerProtoBufModule;
import cn.bytes.jtim.core.module.initialize.SimpleClientInitializeModule;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import static cn.bytes.jtim.common.constant.DefineConstant.NETTY_TCP_SERVER_CLUSTER_KEY;

/**
 * @author maliang@sioniov.com
 * @version 1.0
 * @date 2020/2/13 22:16
 */
@Slf4j
public class SimpleClientInitializeModuleTest {

    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.setHost("127.0.0.1");
        configuration.setPort(1999);

        SimpleClientInitializeModule simpleClientInitializeModule = new SimpleClientInitializeModule(configuration);

        simpleClientInitializeModule
                .then(new SimpleChannelHandlerProtoBufModule().codec(new ProtobufClientHandler()))
                .then(new SimpleConnectionModule())
                .then(getClusterModule(configuration))
        ;

        simpleClientInitializeModule.open();
    }

    public static ClusterModule getClusterModule(Configuration configuration) {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://192.168.50.110:6379");

        RedissonClient redisClient = Redisson.create(config);
        SimpleRedisClusterModule simpleRedisClusterModule =
                new SimpleRedisClusterModule(NETTY_TCP_SERVER_CLUSTER_KEY, redisClient);

        simpleRedisClusterModule.listener(stringClusterServerContentMap -> {

            log.info("客户端监听: {}", stringClusterServerContentMap);
        });
        return simpleRedisClusterModule;
    }


}
