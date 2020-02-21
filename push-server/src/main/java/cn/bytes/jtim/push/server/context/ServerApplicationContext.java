package cn.bytes.jtim.push.server.context;

import cn.bytes.jtim.core.config.Configuration;
import cn.bytes.jtim.core.config.SocketConfig;
import cn.bytes.jtim.push.server.config.PushServerConfigurationProperties;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @version 1.0
 * @date 2020/2/21 12:32
 */
@EnableConfigurationProperties(PushServerConfigurationProperties.class)
@Slf4j
@Getter
public class ServerApplicationContext {

    private PushServerConfigurationProperties pushServerConfigurationProperties;

    public ServerApplicationContext(PushServerConfigurationProperties pushServerConfigurationProperties) {
        this.pushServerConfigurationProperties = pushServerConfigurationProperties;
    }

    protected Configuration getTcpConfiguration() {
        PushServerConfigurationProperties.NettyTcpConfigurationProperties config =
                pushServerConfigurationProperties.getTcp();
        return Configuration.builder()
                .host(config.getHost())
                .port(config.getPort())
                .bossThreads(config.getBossThreads())
                .heartbeatTime(config.getHeartbeatTime())
                .useLinuxNativeEpoll(config.isUseLinuxNativeEpoll())
                .workerThreads(config.getWorkerThreads())
//                .maxHttpContentLength(config.getMaxHttpContentLength())
//                .maxWebsocketFrameSize(config.getMaxWebsocketFrameSize())
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
