package cn.bytes.jtim.core.server;

import cn.bytes.jtim.core.config.Configuration;
import io.netty.channel.ChannelPipeline;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyTcpServer extends NettyServer {

    public NettyTcpServer(Configuration configuration) {
        super(configuration);
    }

    @Override
    public void channelHandlerOptions(ChannelPipeline pipeline) {
//        pipeline.addLast(getChannelHandlerManager().getChannelHandlerArrays());
    }

}
