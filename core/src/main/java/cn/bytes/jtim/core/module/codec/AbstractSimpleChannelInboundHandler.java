package cn.bytes.jtim.core.module.codec;

import cn.bytes.jtim.core.module.handler.ChannelHandlerModule;
import cn.bytes.jtim.core.module.handler.DefineChannelHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.Getter;

/**
 * @version 1.0
 * @date 2020/2/14 15:29
 */
@Getter
@ChannelHandler.Sharable
public abstract class AbstractSimpleChannelInboundHandler<T>
        extends SimpleChannelInboundHandler<T> implements DefineChannelHandler {

    private ChannelHandlerModule channelHandlerModule;

    @Override
    public void bindChannelHandlerModule(ChannelHandlerModule channelHandlerModule) {
        this.channelHandlerModule = channelHandlerModule;
    }
}
