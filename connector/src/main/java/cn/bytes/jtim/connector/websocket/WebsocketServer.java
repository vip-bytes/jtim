package cn.bytes.jtim.connector.websocket;

import cn.bytes.jtim.core.channel.config.Configuration;
import cn.bytes.jtim.core.channel.module.initialize.InitializeModule;
import cn.bytes.jtim.core.channel.module.initialize.SimpleServerInitializeModule;
import org.springframework.beans.factory.InitializingBean;

/**
 * @version 1.0
 * @date 2020/3/17 10:17
 */
@org.springframework.context.annotation.Configuration
public class WebsocketServer implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {

        Configuration configuration = new Configuration();
        InitializeModule websocketServerInitialize = new WebsocketServerInitialize(configuration);
        websocketServerInitialize.open();
    }

    static class WebsocketServerInitialize extends SimpleServerInitializeModule {

        public WebsocketServerInitialize(Configuration configuration) {
            super(configuration);
        }

    }

}
