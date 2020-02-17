package cn.bytes.jtim.broker.config;

import cn.bytes.jtim.core.config.Configuration;
import cn.bytes.jtim.core.config.SocketConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @version 1.0
 * @date 2020/2/12 20:42
 */
@EnableConfigurationProperties({NettyConfigurationProperties.class})
@Slf4j
public class InitializingConfiguration implements InitializingBean {

    private NettyConfigurationProperties nettyConfigurationProperties;

    public InitializingConfiguration(NettyConfigurationProperties nettyConfigurationProperties) {
        this.nettyConfigurationProperties = nettyConfigurationProperties;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }

    public Configuration getTcpConfiguration() {
        return new Configuration(this.convert(nettyConfigurationProperties.getTcp()));
    }

    public Configuration getWebsocketConfiguration() {
        return new Configuration(this.convert(nettyConfigurationProperties.getWebsocket()));
    }

    private Configuration convert(NettyConfigurationProperties.Properties config) {
        return Configuration.builder()
                .host(config.getHost())
                .port(config.getPort())
                .bossThreads(config.getBossThreads())
                .heartReadTime(config.getHeartReadTime())
                .useLinuxNativeEpoll(config.isUseLinuxNativeEpoll())
                .workerThreads(config.getWorkerThreads())
                .maxHttpContentLength(config.getMaxHttpContentLength())
                .maxWebsocketFrameSize(config.getMaxWebsocketFrameSize())
                .socketConfig(
                        SocketConfig.builder()
                                .acceptBackLog(config.getSocketConfig().getAcceptBackLog())
                                .keepAlive(config.getSocketConfig().isKeepAlive())
                                .noDelay(config.getSocketConfig().isNoDelay())
                                .receiveBufferSize(config.getSocketConfig().getReceiveBufferSize())
                                .sendBufferSize(config.getSocketConfig().getSendBufferSize())
                                .writeBufferWaterHigh(config.getSocketConfig().getWriteBufferWaterHigh())
                                .writeBufferWaterLow(config.getSocketConfig().getWriteBufferWaterLow())
                                .build()
                )
                .build();
    }

}
