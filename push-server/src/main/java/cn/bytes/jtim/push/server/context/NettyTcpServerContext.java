package cn.bytes.jtim.push.server.context;

import cn.bytes.jtim.core.module.connection.ConnectionModule;
import cn.bytes.jtim.core.module.connection.SimpleConnectionModule;
import cn.bytes.jtim.core.module.handler.ChannelHandlerModule;
import cn.bytes.jtim.core.module.handler.SimpleChannelHandlerProtoBufModule;
import cn.bytes.jtim.core.module.initialize.SimpleServerInitializeModule;
import cn.bytes.jtim.core.module.route.RouteModule;
import cn.bytes.jtim.core.module.route.SimpleRedisRouteModule;
import cn.bytes.jtim.core.module.route.selector.RouteKey;
import cn.bytes.jtim.push.server.config.PushServerConfigurationProperties;
import cn.bytes.jtim.push.server.module.codec.ProtobufServerCodec;
import lombok.Getter;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

import java.util.Objects;

import static cn.bytes.jtim.common.constant.DefineConstant.BYTES_PUSH_SERVER_TCP;

/**
 * @version 1.0
 * @date 2020/2/21 12:41
 */
@Getter
@ConditionalOnProperty(value = "bytes.push.server.tcp.enable", havingValue = "true")
public class NettyTcpServerContext extends ServerApplicationContext {

    public NettyTcpServerContext(PushServerConfigurationProperties pushServerConfigurationProperties) {
        super(pushServerConfigurationProperties);
    }

    @Bean
    public ChannelHandlerModule channelHandlerModule(ProtobufServerCodec protobufServerCodec) {
        return new SimpleChannelHandlerProtoBufModule().codec(protobufServerCodec);
    }

    @Bean
    public ProtobufServerCodec protobufServerCodec() {
        return new ProtobufServerCodec();
    }

    @Bean
    public ConnectionModule connectionModule() {
        return new SimpleConnectionModule();
    }

    @Bean
    public RouteModule clusterModule(RedissonClient redissonClient) {
        RouteModule routeModule = new SimpleRedisRouteModule(BYTES_PUSH_SERVER_TCP, redissonClient);

        routeModule.route(RouteKey.builder()
                .bind(true)
                .id(getId())
                .host(getTcpConfiguration().getHost())
                .port(getTcpConfiguration().getPort())
                .build());

        return routeModule;
    }

    private String getId() {
        return String.valueOf(
                Math.abs(Objects.hash(super.getTcpConfiguration().getHost(),
                        super.getTcpConfiguration().getPort())));
    }

    /**
     * netty tcp 内部通信
     *
     * @return
     */
    @Bean("tcp")
    public SimpleServerInitializeModule serverInitializeModule(
            ChannelHandlerModule channelHandlerModule,
            ConnectionModule connectionModule,
            RouteModule routeModule) {
        SimpleServerInitializeModule serverInitializeModule = new SimpleServerInitializeModule(super.getTcpConfiguration());

        //编解码处理器
        serverInitializeModule.then(channelHandlerModule)
                //连接处理器模块
                .then(connectionModule)
                //集群处理模块
                .then(routeModule)
        ;

        serverInitializeModule.open();

        return serverInitializeModule;
    }

}
