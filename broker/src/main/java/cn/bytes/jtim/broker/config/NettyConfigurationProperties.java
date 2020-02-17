package cn.bytes.jtim.broker.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Objects;

import static cn.bytes.jtim.common.constant.DefineConstant.PREFIX_PROPER;

/**
 * @version 1.0
 * @date 2020/2/12 21:13
 */
@ConfigurationProperties(PREFIX_PROPER)
@Data
public class NettyConfigurationProperties {

    private Properties tcp;

    private Properties websocket;

    public boolean isEnableWebsocket() {
        return Objects.nonNull(websocket) && websocket.isEnable();
    }

    public boolean isEnableTcp() {
        return Objects.nonNull(tcp) && tcp.isEnable();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Properties {

        private boolean enable;

        private String host = "0.0.0.0";

        private int port = 1999;

        private int bossThreads = 0; // 0 = current_processors_amount * 2,单个端口设置1

        private int workerThreads = 0; // 0 = current_processors_amount * 2

        private boolean useLinuxNativeEpoll = false;

        private int heartReadTime = 30;

        private int maxWebsocketFrameSize = 64 * 1024;

        private int maxHttpContentLength = 64 * 1024;

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
