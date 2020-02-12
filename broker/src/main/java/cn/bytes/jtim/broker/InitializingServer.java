package cn.bytes.jtim.broker;

import cn.bytes.jtim.broker.config.NettyServerProperties;
import cn.bytes.jtim.core.config.Configuration;
import cn.bytes.jtim.core.config.SocketConfig;
import cn.bytes.jtim.core.server.NettyTcpServer;
import cn.bytes.jtim.core.server.NettyWebSocketServer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author maliang@sioniov.com
 * @version 1.0
 * @date 2020/2/12 20:42
 */
@EnableConfigurationProperties({NettyServerProperties.class})
public class InitializingServer implements InitializingBean {

    @Autowired
    private NettyServerProperties nettyServerProperties;

    @Override
    public void afterPropertiesSet() throws Exception {

        if (nettyServerProperties.isEnableTcp()) {
            new NettyTcpServer(this.builderConfig(nettyServerProperties.getTcp())).start();
        }

        if (nettyServerProperties.isEnableWebsocket()) {
            new NettyWebSocketServer(this.builderConfig(nettyServerProperties.getTcp())).start();
        }
    }

    private Configuration builderConfig(NettyServerProperties.Properties config) {
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
