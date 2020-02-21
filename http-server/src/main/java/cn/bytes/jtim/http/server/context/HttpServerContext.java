package cn.bytes.jtim.http.server.context;

import cn.bytes.jtim.core.module.connection.ConnectionModule;
import cn.bytes.jtim.core.module.connection.SimpleConnectionModule;
import cn.bytes.jtim.core.module.handler.ChannelHandlerModule;
import cn.bytes.jtim.core.module.handler.SimpleChannelHandlerProtoBufModule;
import cn.bytes.jtim.core.module.route.RouteModule;
import cn.bytes.jtim.http.server.config.HttpServerConfigurationProperties;
import cn.bytes.jtim.http.server.module.codec.ProtobufClientCodec;
import cn.bytes.jtim.http.server.module.route.SimpleClientRouteModule;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;

/**
 * @version 1.0
 * @date 2020/2/21 13:56
 */
public class HttpServerContext extends ServerApplicationContext {

    public HttpServerContext(HttpServerConfigurationProperties httpServerConfigurationProperties) {
        super(httpServerConfigurationProperties);
    }

    @Bean
    public ProtobufClientCodec protobufServerCodec() {
        return new ProtobufClientCodec();
    }

    @Bean
    public ChannelHandlerModule channelHandlerProtoBufModule(ProtobufClientCodec protobufClientCodec) {
        return new SimpleChannelHandlerProtoBufModule().codec(protobufClientCodec);
    }

    @Bean
    public ConnectionModule connectionModule() {
        return new SimpleConnectionModule();
    }

    @Bean
    public RouteModule routeModule(RedissonClient redissonClient, ChannelHandlerModule channelHandlerModule,
                                   ConnectionModule connectionModule) {
        return new SimpleClientRouteModule(redissonClient, channelHandlerModule, connectionModule);
    }


}
