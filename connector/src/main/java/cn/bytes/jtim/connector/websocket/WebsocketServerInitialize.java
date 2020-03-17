package cn.bytes.jtim.connector.websocket;

import cn.bytes.jtim.core.channel.config.Configuration;
import cn.bytes.jtim.core.channel.module.initialize.SimpleServerInitializeModule;

/**
 * @version 1.0
 * @date 2020/3/17 10:17
 */
public class WebsocketServerInitialize extends SimpleServerInitializeModule {

    public WebsocketServerInitialize(Configuration configuration) {
        super(configuration);
    }

}
