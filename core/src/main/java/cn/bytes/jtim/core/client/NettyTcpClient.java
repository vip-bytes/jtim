package cn.bytes.jtim.core.client;

import cn.bytes.jtim.core.config.Configuration;
import cn.bytes.jtim.core.handler.ProtobufTcpClientHandler;
import io.netty.channel.ChannelPipeline;
import lombok.extern.slf4j.Slf4j;

/**
 * @version 1.0
 * @date 2020/2/10 23:10
 */
@Slf4j
public class NettyTcpClient extends NettyClient {

    public NettyTcpClient(Configuration configuration) {
        super(configuration);
    }

    @Override
    public void channelHandlerOptions(ChannelPipeline pipeline) {
//        pipeline.addLast(getChannelHandlerManager().getChannelHandlerArrays());
        super.addLastAndBindManager(new ProtobufTcpClientHandler());
    }
}
