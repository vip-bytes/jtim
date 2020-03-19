package cn.bytes.jtim.connector.websocket;

import cn.bytes.jtim.connector.configuration.WebsocketConfigurationProperties;
import cn.bytes.jtim.core.channel.config.Configuration;
import cn.bytes.jtim.core.channel.config.SocketConfig;
import cn.bytes.jtim.core.channel.module.handler.SimpleChannelHandlerModule;
import cn.bytes.jtim.core.channel.module.initialize.InitializeModule;
import cn.bytes.jtim.core.channel.module.initialize.SimpleServerInitializeModule;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.concurrent.TimeUnit;

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

    private boolean isLinux() {
        String osName = System.getProperty("os.name");
        return osName.toLowerCase().contains("linux");
    }

    /**
     * websocket服务
     */
    static class WebsocketServerInitialize extends SimpleServerInitializeModule {

        public WebsocketServerInitialize(Configuration configuration) {
            super(configuration);
            this.then(
                    new WebsocketServerChannelHandler(configuration)
                            .codec(new WebsocketHttpRequestCodecInboundHandler())
                            .codec(new WebsocketFrameCodecInboundHandler())
            );
        }
    }

    /**
     * websocket处理
     */
    static class WebsocketServerChannelHandler extends SimpleChannelHandlerModule {

        private Configuration configuration;

        public WebsocketServerChannelHandler(Configuration configuration) {
            this.configuration = configuration;
        }

        @Override
        public void optionHandler0(ChannelPipeline channelPipeline) {
            channelPipeline
                    .addLast(new HttpServerCodec())
                    .addLast(new HttpObjectAggregator(this.configuration.getMaxHttpContentLength()))
                    .addLast(new WebSocketServerCompressionHandler())
                    .addLast(new WebSocketServerProtocolHandler("/ws", null, true, this.configuration.getMaxWebsocketFrameSize()))
                    .addLast(new IdleStateHandler(this.configuration.getHeartbeatTime(), 0, 0, TimeUnit.SECONDS));
        }
    }

    /**
     * 配置信息
     *
     * @return
     */
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

}
