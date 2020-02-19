package cn.bytes.jtim.broker.config;

import cn.bytes.jtim.broker.module.connection.SimpleConnectionWebsocketServerModule;
import cn.bytes.jtim.broker.module.handler.SimpleChannelHandlerWebsocketModule;
import cn.bytes.jtim.broker.module.handler.codec.NettyWebsocketServerProtobufCodec;
import cn.bytes.jtim.core.module.server.NettyWebsocketServer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

import static cn.bytes.jtim.common.constant.DefineConstant.PROPER_WEBSOCKET_ENABLE;

/**
 * @version 1.0
 * @date 2020/2/17 22:26
 */
@ConditionalOnProperty(name = PROPER_WEBSOCKET_ENABLE, havingValue = "true", matchIfMissing = false)
public class NettyWebsocketServerConfiguration extends InitializingConfiguration {

    public NettyWebsocketServerConfiguration(NettyConfigurationProperties nettyConfigurationProperties) {
        super(nettyConfigurationProperties);
    }

    @Bean
    public NettyWebsocketServerProtobufCodec nettyWebsocketServerProtobufCodec() {
        return new NettyWebsocketServerProtobufCodec();
    }

    @Bean
    public SimpleChannelHandlerWebsocketModule simpleChannelHandlerWebsocketModule(
            NettyWebsocketServerProtobufCodec nettyWebsocketServerProtobufCodec) {
        SimpleChannelHandlerWebsocketModule simpleChannelHandlerTcpModule = new SimpleChannelHandlerWebsocketModule();
        simpleChannelHandlerTcpModule.codec(nettyWebsocketServerProtobufCodec);
        return simpleChannelHandlerTcpModule;
    }

    @Bean
    public SimpleConnectionWebsocketServerModule simpleConnectionWebsocketServerModule() {
        return new SimpleConnectionWebsocketServerModule();
    }

    @Bean
    public NettyWebsocketServer websocketServer(
            SimpleChannelHandlerWebsocketModule simpleChannelHandlerWebsocketModule,
            SimpleConnectionWebsocketServerModule simpleConnectionWebsocketServerModule) {
        NettyWebsocketServer nettyWebsocketServer = new NettyWebsocketServer(super.getWebsocketConfiguration());
        nettyWebsocketServer
                .then(simpleChannelHandlerWebsocketModule)
                .then(simpleConnectionWebsocketServerModule);
        nettyWebsocketServer.open();
        return nettyWebsocketServer;
    }


}
