package cn.bytes.jtim.http.server;

import cn.bytes.jtim.core.config.Configuration;
import cn.bytes.jtim.core.module.connection.SimpleConnectionModule;
import cn.bytes.jtim.core.module.handler.SimpleChannelHandlerProtoBufModule;
import cn.bytes.jtim.core.module.initialize.SimpleClientInitializeModule;
import cn.bytes.jtim.core.module.retry.SimpleRetryModule;
import cn.bytes.jtim.core.module.route.RouteModule;
import cn.bytes.jtim.core.module.route.SimpleRedisRouteModule;
import cn.bytes.jtim.core.module.task.SimpleHeartbeatRunTaskModule;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import static cn.bytes.jtim.common.constant.DefineConstant.BYTES_PUSH_SERVER_TCP;

/**
 * @author maliang@sioniov.com
 * @version 1.0
 * @date 2020/2/13 22:16
 */
@Slf4j
public class SimpleClientInitializeModuleTest {

    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.setHost("192.168.0.102");
        configuration.setPort(2020);

        SimpleClientInitializeModule simpleClientInitializeModule = new SimpleClientInitializeModule(configuration);

        simpleClientInitializeModule
                .then(SimpleRetryModule.builder().loop(true).delay(5).build())

                .then(new SimpleChannelHandlerProtoBufModule().codec(new ProtobufClientHandler()))

                //bug ConnectionModule 没有添加进去,创建子模块绑定到的信息为空
                .then(new SimpleConnectionModule().then(new SimpleHeartbeatRunTaskModule()))

                .then(getClusterModule(configuration));

        simpleClientInitializeModule.open();
    }

    public static RouteModule getClusterModule(Configuration configuration) {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://192.168.50.110:6379");

        RedissonClient redisClient = Redisson.create(config);
        SimpleRedisRouteModule simpleRedisClusterModule =
                new SimpleRedisRouteModule(BYTES_PUSH_SERVER_TCP, redisClient);

        simpleRedisClusterModule.listener(stringClusterServerContentMap -> {

            log.info("客户端监听: {}", stringClusterServerContentMap);
        });
        return simpleRedisClusterModule;
    }


}
