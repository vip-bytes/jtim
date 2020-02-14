package cn.bytes.jtim.core.handler;

import cn.bytes.jtim.core.Actuator;
import cn.bytes.jtim.core.protocol.protobuf.Message;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * @version 1.0
 * @date 2020/2/14 14:44
 */
public abstract class DefaultChannelInitializer extends ChannelInitializer<Channel>
        implements ChannelHandlerManager<AbstractSimpleChannelInboundHandler<Message>>, Actuator {

    private LinkedList<AbstractSimpleChannelInboundHandler<Message>> channelInboundHandlers
            = new LinkedList<>();

    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline().addLast(new ProtobufVarint32FrameDecoder());
        channel.pipeline().addLast(new ProtobufDecoder(Message.getDefaultInstance()));
        channel.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
        channel.pipeline().addLast(new ProtobufEncoder());
    }

    @Override
    public ChannelHandlerManager<AbstractSimpleChannelInboundHandler<Message>> addLast(AbstractSimpleChannelInboundHandler<Message> channelHandler) {

        if (Objects.isNull(channelHandler)) {
            return this;
        }
        channelHandler.setActuator(this);
        channelInboundHandlers.addLast(channelHandler);
        return this;
    }

    @Override
    public AbstractSimpleChannelInboundHandler<Message>[] defineHandlerToArray() {
        return channelInboundHandlers.toArray(new AbstractSimpleChannelInboundHandler[]{});
    }
}
