package cn.bytes.jtim.core.module.handler.codec;

import cn.bytes.jtim.core.module.handler.ChannelHandlerModule;
import cn.bytes.jtim.core.module.handler.DefineChannelHandler;
import io.netty.channel.ChannelHandler;
import io.netty.handler.codec.MessageToMessageEncoder;
import lombok.Getter;

/**
 * @version 1.0
 * @date 2020/2/14 23:04
 */
@Getter
@ChannelHandler.Sharable
public abstract class AbstractSimpleChannelOutboundHandler<T>
        extends MessageToMessageEncoder<T> implements DefineChannelHandler {

    private ChannelHandlerModule channelHandlerModule;

    @Override
    public void bindChannelHandlerModule(ChannelHandlerModule channelHandlerModule) {
        this.channelHandlerModule = channelHandlerModule;
    }
}
