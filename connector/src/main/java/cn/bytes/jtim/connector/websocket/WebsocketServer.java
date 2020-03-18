package cn.bytes.jtim.connector.websocket;

import cn.bytes.jtim.connector.configuration.WebsocketConfigurationProperties;
import cn.bytes.jtim.core.channel.config.Configuration;
import cn.bytes.jtim.core.channel.config.SocketConfig;
import cn.bytes.jtim.core.channel.module.initialize.InitializeModule;
import cn.bytes.jtim.core.channel.module.initialize.SimpleServerInitializeModule;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @version 1.0
 * @date 2020/3/17 10:17
 */
@org.springframework.context.annotation.Configuration
@EnableConfigurationProperties({WebsocketConfigurationProperties.class})
public class WebsocketServer implements InitializingBean {

    private WebsocketConfigurationProperties websocketConfigurationProperties;

    public WebsocketServer(WebsocketConfigurationProperties websocketConfigurationProperties) {
        this.websocketConfigurationProperties = websocketConfigurationProperties;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        Configuration configuration = getConfiguration();

        InitializeModule websocketServerInitialize = new WebsocketServerInitialize(configuration);
        websocketServerInitialize.open();
    }

    private Configuration getConfiguration() {

        Configuration configuration = new Configuration();

        configuration.setHost(websocketConfigurationProperties.getHost());
        configuration.setPort(websocketConfigurationProperties.getPort());
        configuration.setBossThreads(websocketConfigurationProperties.getBossThreads());
        configuration.setWorkerThreads(websocketConfigurationProperties.getWorkerThreads());
        configuration.setHeartbeatTime(websocketConfigurationProperties.getHeartbeatTime());
        configuration.setMaxHttpContentLength(websocketConfigurationProperties.getMaxHttpContentLength());
        configuration.setMaxWebsocketFrameSize(websocketConfigurationProperties.getMaxWebsocketFrameSize());
        configuration.setUseLinuxNativeEpoll(this.isLinux());

        //
        SocketConfig socketConfig = new SocketConfig();
        socketConfig.setAcceptBackLog(websocketConfigurationProperties.getAcceptBackLog());
        socketConfig.setReceiveBufferSize(websocketConfigurationProperties.getReceiveBufferSize());
        socketConfig.setSendBufferSize(websocketConfigurationProperties.getSendBufferSize());
        socketConfig.setWriteBufferWaterHigh(websocketConfigurationProperties.getWriteBufferWaterHigh());
        socketConfig.setWriteBufferWaterLow(websocketConfigurationProperties.getWriteBufferWaterLow());
        configuration.setSocketConfig(socketConfig);

        return configuration;

    }

    private boolean isLinux() {
        String osName = System.getProperty("os.name");
        return osName.toLowerCase().contains("linux");
    }

    static class WebsocketServerInitialize extends SimpleServerInitializeModule {

        public WebsocketServerInitialize(Configuration configuration) {
            super(configuration);
        }

    }


}
