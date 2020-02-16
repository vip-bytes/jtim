package cn.bytes.jtim.core.module.handler;

import cn.bytes.jtim.core.protocol.protobuf.Message;
import io.netty.channel.ChannelHandler;
import io.netty.handler.codec.MessageToMessageEncoder;
import lombok.Getter;

/**
 * @version 1.0
 * @date 2020/2/14 23:04
 */
@Getter
@ChannelHandler.Sharable
public abstract class AbstractSimpleChannelOutboundHandler
        extends MessageToMessageEncoder<Message> implements DefineChannelHandler {

    private ChannelHandlerModule channelHandlerModule;

    @Override
    public void bindChannelHandlerModule(ChannelHandlerModule channelHandlerModule) {
        this.channelHandlerModule = channelHandlerModule;
    }
}
