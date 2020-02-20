package cn.bytes.jtim.broker.config.tcp;

import cn.bytes.jtim.broker.config.InitializingConfiguration;
import cn.bytes.jtim.broker.config.NettyConfigurationProperties;
import cn.bytes.jtim.broker.module.handler.SimpleChannelHandlerTcpModule;
import cn.bytes.jtim.core.module.cluster.ClusterModule;
import cn.bytes.jtim.core.module.cluster.ClusterServerContent;
import cn.bytes.jtim.core.module.cluster.SimpleRedisClusterModule;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

import java.util.Objects;

import static cn.bytes.jtim.common.constant.DefineConstant.NETTY_TCP_SERVER_CLUSTER_KEY;
import static cn.bytes.jtim.common.constant.DefineConstant.PROPER_TCP_ENABLE;

/**
 * @version 1.0
 * @date 2020/2/20 20:23
 */
@ConditionalOnProperty(name = PROPER_TCP_ENABLE, havingValue = "true")
public class GlobalConfiguration extends InitializingConfiguration {

    public GlobalConfiguration(NettyConfigurationProperties nettyConfigurationProperties) {
        super(nettyConfigurationProperties);
    }

    @Bean
    public SimpleChannelHandlerTcpModule simpleChannelHandlerTcpModule() {
        return new SimpleChannelHandlerTcpModule();
    }

    @Bean
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

}
