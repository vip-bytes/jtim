package cn.bytes.jtim.push.server.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * push server的配置信息
 *
 * @version 1.0
 * @date 2020/2/21 12:18
 */

@ConfigurationProperties(prefix = "bytes.push.server")
@Data
public class PushServerConfigurationProperties {

    private NettyTcpConfigurationProperties tcp;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class NettyTcpConfigurationProperties {

        private boolean enable = false;

        private String host = "0.0.0.0";

        private int port = 2020;

        private int bossThreads = 0; // 0 = current_processors_amount * 2,单个端口设置1

        private int workerThreads = 0; // 0 = current_processors_amount * 2

        private boolean useLinuxNativeEpoll = false;

        private int heartbeatTime = 30;

        private SocketConfig socketConfig = new SocketConfig();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SocketConfig {

        private boolean noDelay = true;

        private int sendBufferSize = 32 * 1024;

        private int receiveBufferSize = 32 * 1024;

        private boolean keepAlive = false;

        private int acceptBackLog = 1024;

        private int writeBufferWaterLow = 32 * 1024;

        private int writeBufferWaterHigh = 64 * 1024;
    }

}
