package cn.bytes.jtim.broker.config.tcp;

import cn.bytes.jtim.broker.config.InitializingConfiguration;
import cn.bytes.jtim.broker.config.NettyConfigurationProperties;
import cn.bytes.jtim.broker.module.connection.SimpleConnectionTcpServerModule;
import cn.bytes.jtim.broker.module.handler.SimpleChannelHandlerTcpModule;
import cn.bytes.jtim.broker.module.handler.codec.ProtobufClientCodec;
import cn.bytes.jtim.core.module.initialize.SimpleServerInitializeModule;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

import static cn.bytes.jtim.common.constant.DefineConstant.PROPER_TCP_ENABLE;

/**
 * @version 1.0
 * @date 2020/2/17 22:26
 */
@ConditionalOnProperty(name = PROPER_TCP_ENABLE, havingValue = "true")
public class NettyTcpClientConfiguration extends InitializingConfiguration {

    public NettyTcpClientConfiguration(NettyConfigurationProperties nettyConfigurationProperties) {
        super(nettyConfigurationProperties);
    }

    @Bean
    public ProtobufClientCodec protobufClientCodec() {
        return new ProtobufClientCodec();
    }

    @Bean
    public SimpleConnectionTcpServerModule simpleConnectionTcpServerModule() {
        return new SimpleConnectionTcpServerModule();
    }

    @Bean("netty-client")
    public SimpleServerInitializeModule serverInitializeModule(
            SimpleChannelHandlerTcpModule simpleChannelHandlerTcpModule,
            SimpleConnectionTcpServerModule simpleConnectionTcpServerModule) {
        SimpleServerInitializeModule serverInitializeModule = new SimpleServerInitializeModule(super.getTcpConfiguration());
        serverInitializeModule
                .then(simpleChannelHandlerTcpModule.codec(protobufClientCodec()))
                .then(simpleConnectionTcpServerModule);
        serverInitializeModule.open();
        return serverInitializeModule;
    }


}
