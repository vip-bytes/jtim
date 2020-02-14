package cn.bytes.jtim.core.handler;

import io.netty.channel.ChannelHandler;

import java.util.List;

/**
 * @version 1.0
 * @date 2020/2/14 14:40
 */
public interface ChannelHandlerManager<V extends ChannelHandler> extends List<V> {

    ChannelHandler[] getChannelHandlerArrays();
}
