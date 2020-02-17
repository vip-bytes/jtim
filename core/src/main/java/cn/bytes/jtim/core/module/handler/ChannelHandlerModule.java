package cn.bytes.jtim.core.module.handler;

import cn.bytes.jtim.core.module.Module;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelPipeline;

/**
 * 处理器模块
 * <p>
 * Sharable 可以用共享 非Sharable的如何添加
 * </p>
 *
 * @version 1.0
 * @date 2020/2/16 22:36
 */
public interface ChannelHandlerModule extends Module {

    void optionHandler(ChannelPipeline channelPipeline);

    ChannelHandlerModule addLast(ChannelHandler... channelHandlers);

}
