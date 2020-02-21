package cn.bytes.jtim.core.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Configuration {

    @Builder.Default
    private String host = "0.0.0.0";

    @Builder.Default
    private int port = 1999;

    @Builder.Default
    private int bossThreads = 1; // 0 = current_processors_amount * 2,单个端口设置1

    @Builder.Default
    private int workerThreads = 10; // 0 = current_processors_amount * 2

    private boolean useLinuxNativeEpoll;

    @Builder.Default
    private int heartbeatTime = 30;

    @Builder.Default
    private int maxWebsocketFrameSize = 64 * 1024;

    @Builder.Default
    private int maxHttpContentLength = 64 * 1024;

    @Builder.Default
    private SocketConfig socketConfig = new SocketConfig();

    public Configuration(Configuration configuration) {
        setHost(configuration.getHost());
        setPort(configuration.getPort());
        setBossThreads(configuration.getBossThreads());
        setWorkerThreads(configuration.getWorkerThreads());
        setUseLinuxNativeEpoll(configuration.isUseLinuxNativeEpoll());
        setHeartbeatTime(configuration.getHeartbeatTime());
        setSocketConfig(configuration.getSocketConfig());
        setMaxWebsocketFrameSize(configuration.getMaxWebsocketFrameSize());
        setMaxHttpContentLength(configuration.getMaxHttpContentLength());
    }

}
