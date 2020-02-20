package cn.bytes.jtim.broker.config.tcp;

import cn.bytes.jtim.broker.config.InitializingConfiguration;
import cn.bytes.jtim.broker.config.NettyConfigurationProperties;
import cn.bytes.jtim.broker.module.connection.SimpleConnectionTcpServerModule;
import cn.bytes.jtim.broker.module.handler.SimpleChannelHandlerTcpModule;
import cn.bytes.jtim.broker.module.handler.codec.ProtobufServerCodec;
import cn.bytes.jtim.core.module.cluster.ClusterModule;
import cn.bytes.jtim.core.module.cluster.ClusterServerContent;
import cn.bytes.jtim.core.module.cluster.SimpleRedisClusterModule;
import cn.bytes.jtim.core.module.initialize.SimpleServerInitializeModule;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.util.Objects;

import static cn.bytes.jtim.common.constant.DefineConstant.NETTY_TCP_SERVER_CLUSTER_KEY;
import static cn.bytes.jtim.common.constant.DefineConstant.PROPER_TCP_ENABLE;

/**
 * @version 1.0
 * @date 2020/2/17 22:26
 */
@ConditionalOnProperty(name = PROPER_TCP_ENABLE, havingValue = "true")
@Import({GlobalConfiguration.class})
public class NettyTcpServerConfiguration extends InitializingConfiguration {

    public NettyTcpServerConfiguration(NettyConfigurationProperties nettyConfigurationProperties) {
        super(nettyConfigurationProperties);
    }

    @Bean
    public ProtobufServerCodec protobufServerCodec() {
        return new ProtobufServerCodec();
    }

    @Bean("server-connection")
    public SimpleConnectionTcpServerModule simpleConnectionTcpServerModule() {
        return new SimpleConnectionTcpServerModule();
    }

    @Bean("server-cluster")
    public ClusterModule clusterModule(RedissonClient redissonClient) {
        ClusterModule clusterModule = new SimpleRedisClusterModule(NETTY_TCP_SERVER_CLUSTER_KEY, redissonClient);
        clusterModule.content(ClusterServerContent.builder()
                .host(super.getTcpConfiguration().getHost())
                .port(super.getTcpConfiguration().getPort())
                .id(getId())
                .build());
        return clusterModule;
    }

    private String getId() {
        return String.valueOf(
                Math.abs(Objects.hash(super.getTcpConfiguration().getHost(),
                        super.getTcpConfiguration().getPort())));
    }

    @Bean("server")
    public SimpleServerInitializeModule serverInitializeModule(
            SimpleChannelHandlerTcpModule simpleChannelHandlerTcpModule,
            @Qualifier("server-connection") SimpleConnectionTcpServerModule simpleConnectionTcpServerModule,
            @Qualifier("server-cluster") ClusterModule clusterModule) {

        SimpleServerInitializeModule serverInitializeModule = new SimpleServerInitializeModule(super.getTcpConfiguration());
        serverInitializeModule
                .then(simpleChannelHandlerTcpModule.codec(protobufServerCodec()))
                .then(simpleConnectionTcpServerModule)
                .then(clusterModule);
        serverInitializeModule.open();
        return serverInitializeModule;
    }


}
