package cn.bytes.jtim.core.module.client;

import cn.bytes.jtim.core.config.Configuration;
import cn.bytes.jtim.core.module.handler.ChannelHandlerModule;
import cn.bytes.jtim.core.module.initialize.SimpleClientInitializeModule;

/**
 * @version 1.0
 * @date 2020/2/16 23:08
 */
public class NettyTcpClient extends SimpleClientInitializeModule {

    public NettyTcpClient(Configuration configuration) {
        super(configuration);
    }

    @Override
    protected void channelHandlerOptions(ChannelHandlerModule channelHandlerModule) {

    }

}
