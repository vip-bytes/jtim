package cn.bytes.jtim.core.handler;

import cn.bytes.jtim.core.Actuator;
import cn.bytes.jtim.core.protocol.protobuf.Message;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

import java.util.LinkedList;
import java.util.Objects;

/**
 * @version 1.0
 * @date 2020/2/14 14:44
 */
public abstract class DefaultHandlerManagerInitializer extends ChannelInitializer<Channel>
        implements
        ChannelHandlerManagerInitializer<DefineChannelHandler<Message>> {

    private LinkedList<DefineChannelHandler<Message>> defineChannelHandlers = new LinkedList<>();

    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline().addLast(new ProtobufVarint32FrameDecoder());
        channel.pipeline().addLast(new ProtobufDecoder(Message.getDefaultInstance()));
        channel.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
        channel.pipeline().addLast(new ProtobufEncoder());
    }

    @Override
    public ChannelHandlerManagerInitializer<DefineChannelHandler<Message>> addLastAndBindManager(DefineChannelHandler<Message> channelHandler) {

        if (Objects.isNull(channelHandler)) {
            return this;
        }

        channelHandler.bindActuator(this);
        defineChannelHandlers.add(channelHandler);
        return this;
    }

    @Override
    public DefineChannelHandler<Message>[] defineHandlerToArray() {
        return this.defineChannelHandlers.toArray(new DefineChannelHandler[]{});
    }

}
