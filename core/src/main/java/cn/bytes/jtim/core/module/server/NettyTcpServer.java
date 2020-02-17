package cn.bytes.jtim.core.module.server;

import cn.bytes.jtim.core.config.Configuration;
import cn.bytes.jtim.core.module.initialize.SimpleServerInitializeModule;

/**
 * @version 1.0
 * @date 2020/2/16 22:46
 */
public class NettyTcpServer extends SimpleServerInitializeModule {

    public NettyTcpServer(Configuration configuration) {
        super(configuration);
    }

}
