package cn.bytes.jtim.core.server;

import cn.bytes.jtim.core.config.Configuration;
import io.netty.channel.ChannelPipeline;

/**
 * @author maliang@sioniov.com
 * @version 1.0
 * @date 2020/2/10 23:09
 */
public class NettySocketIoServer extends NettyServer {

    public NettySocketIoServer(Configuration configuration) {
        super(configuration);
    }

    @Override
    public void channelHandlerOptions(ChannelPipeline pipeline) {

    }

}
