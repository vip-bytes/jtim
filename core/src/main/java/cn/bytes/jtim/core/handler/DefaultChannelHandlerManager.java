package cn.bytes.jtim.core.handler;

import cn.bytes.jtim.core.protocol.protobuf.Message;
import io.netty.channel.ChannelHandler;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

import java.util.LinkedList;

/**
 * @version 1.0
 * @date 2020/2/14 14:44
 */
public class DefaultChannelHandlerManager extends LinkedList<ChannelHandler>
        implements ChannelHandlerManager<ChannelHandler> {

    /**
     * 初始创建Protobuf的处理器
     */
    public void initProtoBufHandler() {
        addLast(new ProtobufVarint32FrameDecoder());
        addLast(new ProtobufDecoder(Message.getDefaultInstance()));
        addLast(new ProtobufVarint32LengthFieldPrepender());
        addLast(new ProtobufEncoder());
    }

    @Override
    public ChannelHandler[] getChannelHandlerArrays() {
        return this.toArray(new ChannelHandler[]{});
    }
}
