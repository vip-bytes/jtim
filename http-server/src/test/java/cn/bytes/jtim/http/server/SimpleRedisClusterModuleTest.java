package cn.bytes.jtim.http.server;

import cn.bytes.jtim.core.module.route.ClusterServerContent;
import cn.bytes.jtim.core.module.route.SimpleRedisRouteModule;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import static cn.bytes.jtim.common.constant.DefineConstant.BYTES_PUSH_SERVER_TCP;

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
        SimpleRedisRouteModule simpleRedisClusterModule =
                new SimpleRedisRouteModule(BYTES_PUSH_SERVER_TCP, redisClient);

        new Thread(() -> simpleRedisClusterModule.listener(clusterServerContents -> {
            System.out.println("监听到数据:  " + clusterServerContents);
        })).start();

        ClusterServerContent clusterServerContent = ClusterServerContent.builder()
                .host("127.0.0.1")
                .port(1999)
                .tag("tcp")
                .build();

//        simpleRedisClusterModule.content(clusterServerContent);
    }
}
