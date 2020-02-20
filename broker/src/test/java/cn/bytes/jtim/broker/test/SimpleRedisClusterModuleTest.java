package cn.bytes.jtim.broker.test;

import cn.bytes.jtim.core.module.cluster.ClusterServerContent;
import cn.bytes.jtim.core.module.cluster.SimpleRedisClusterModule;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import static cn.bytes.jtim.common.constant.DefineConstant.NETTY_TCP_SERVER_CLUSTER_KEY;

/**
 * @version 1.0
 * @date 2020/2/18 18:16
 */
public class SimpleRedisClusterModuleTest {

    public static void main(String[] args) {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://192.168.50.110:6379");

        RedissonClient redisClient = Redisson.create(config);
        SimpleRedisClusterModule simpleRedisClusterModule =
                new SimpleRedisClusterModule(NETTY_TCP_SERVER_CLUSTER_KEY, redisClient);

        new Thread(() -> simpleRedisClusterModule.listener(clusterServerContents -> {
            System.out.println("监听到数据:  " + clusterServerContents);
        })).start();

        ClusterServerContent clusterServerContent = ClusterServerContent.builder()
                .host("127.0.0.1")
                .port(1999)
                .tag("tcp")
                .build();

        simpleRedisClusterModule.content(clusterServerContent);
    }
}
