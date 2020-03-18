package cn.bytes.jtim.connector.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @version 1.0
 * @date 2020/3/18 16:00
 */
@Data
@ConfigurationProperties(prefix = "websocket.config")
public class WebsocketConfigurationProperties {

    private String host = "0.0.0.0";

    private int port = 1999;

    private int bossThreads = 1; // 0 = current_processors_amount * 2,单个端口设置1

    private int workerThreads = 0; // 0 = current_processors_amount * 2

//    private boolean useLinuxNativeEpoll;

    private int heartbeatTime = 30;

    private int maxWebsocketFrameSize = 64 * 1024;

    private int maxHttpContentLength = 64 * 1024;

    private int sendBufferSize = 32 * 1024;

    private int receiveBufferSize = 32 * 1024;

    private int acceptBackLog = 1024;

    private int writeBufferWaterLow = 32 * 1024;

    private int writeBufferWaterHigh = 64 * 1024;

}
