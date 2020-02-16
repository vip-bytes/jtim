package cn.bytes.jtim.core.module.handler;

import io.netty.channel.ChannelHandler;

/**
 * 根据自定义的处理器能获取到对应的容器信息
 *
 * @version 1.0
 * @date 2020/2/16 23:43
 */
public interface DefineChannelHandler extends ChannelHandler {

    void bindChannelHandlerModule(ChannelHandlerModule channelHandlerModule);

}
