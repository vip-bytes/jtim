package cn.bytes.jtim.broker.config;

import cn.bytes.jtim.broker.module.connection.SimpleConnectionTcpServerModule;
import cn.bytes.jtim.broker.module.handler.SimpleChannelHandlerTcpModule;
import cn.bytes.jtim.broker.module.handler.codec.NettyTcpServerProtobufCodec;
import cn.bytes.jtim.core.module.server.NettyTcpServer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

import static cn.bytes.jtim.common.constant.DefineConstant.PROPER_TCP_ENABLE;

/**
 * @version 1.0
 * @date 2020/2/17 22:26
 */
@ConditionalOnProperty(name = PROPER_TCP_ENABLE, havingValue = "true", matchIfMissing = false)
public class NettyTcpServerConfiguration extends InitializingConfiguration {

    public NettyTcpServerConfiguration(NettyConfigurationProperties nettyConfigurationProperties) {
        super(nettyConfigurationProperties);
    }

    @Bean
    public NettyTcpServerProtobufCodec nettyTcpServerProtobufCodec() {
        return new NettyTcpServerProtobufCodec();
    }

    @Bean
    public SimpleChannelHandlerTcpModule simpleChannelHandlerTcpModule(
            NettyTcpServerProtobufCodec nettyTcpServerProtobufCodec) {
        SimpleChannelHandlerTcpModule simpleChannelHandlerTcpModule = new SimpleChannelHandlerTcpModule();
        simpleChannelHandlerTcpModule.codec(nettyTcpServerProtobufCodec);
        return simpleChannelHandlerTcpModule;
    }

    @Bean
    public SimpleConnectionTcpServerModule simpleConnectionTcpServerModule() {
        return new SimpleConnectionTcpServerModule();
    }

    @Bean
    public NettyTcpServer nettyTcpServer(
            SimpleChannelHandlerTcpModule simpleChannelHandlerTcpModule,
            SimpleConnectionTcpServerModule simpleConnectionTcpServerModule) {
        NettyTcpServer nettyTcpServer = new NettyTcpServer(super.getTcpConfiguration());
        nettyTcpServer
                .then(simpleChannelHandlerTcpModule)
                .then(simpleConnectionTcpServerModule);
        nettyTcpServer.open();
        return nettyTcpServer;
    }


}
