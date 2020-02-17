package cn.bytes.jtim.core.module.server;

import cn.bytes.jtim.core.config.Configuration;
import cn.bytes.jtim.core.module.initialize.SimpleServerInitializeModule;

/**
 * @version 1.0
 * @date 2020/2/17 22:04
 */
public class NettyWebsocketServer extends SimpleServerInitializeModule {

    public NettyWebsocketServer(Configuration configuration) {
        super(configuration);
    }

}
