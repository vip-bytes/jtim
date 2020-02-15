package cn.bytes.jtim.core.server;

import cn.bytes.jtim.core.config.Configuration;
import cn.bytes.jtim.core.connection.DefineConnectionManager;
import cn.bytes.jtim.core.handler.DefineHandlerManager;
import cn.bytes.jtim.core.module.Module;
import cn.bytes.jtim.core.module.ModuleManager;
import io.netty.channel.ChannelPipeline;

/**
 * @version 1.0
 * @date 2020/2/10 23:09
 */
public class NettySocketIoServer extends NettyServer {

    public NettySocketIoServer(Configuration configuration, ModuleManager moduleManager) {
        super(configuration, moduleManager);
    }
}
