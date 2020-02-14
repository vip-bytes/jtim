package cn.bytes.jtim.core.server;

import cn.bytes.jtim.core.config.Configuration;
import cn.bytes.jtim.core.handler.ProtobufServerHandler;
import cn.bytes.jtim.core.protocol.protobuf.Message;
import com.google.common.collect.Lists;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class NettyTcpServer extends NettyServer {

    public NettyTcpServer(Configuration configuration) {
        super(configuration);
    }

    @Override
    public void channelHandlerOptions(ChannelPipeline pipeline) {
        pipeline.addLast(getChannelHandlerManager().getChannelHandlerArrays())
                .addLast(new ProtobufServerHandler());
    }

}
