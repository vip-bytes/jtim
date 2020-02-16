package cn.bytes.jtim.core.module.handler;

import cn.bytes.jtim.core.module.Module;
import io.netty.channel.ChannelHandler;

/**
 * @version 1.0
 * @date 2020/2/16 22:36
 */
public interface ChannelHandlerModule extends Module {

    ChannelHandlerModule addLast(ChannelHandler channelHandler);

    ChannelHandlerModule remove(ChannelHandler channelHandler);

    ChannelHandler[] getChannelHandlers();

}
