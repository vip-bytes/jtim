package cn.bytes.jtim.core.channel.module.handler;

import cn.bytes.jtim.core.module.Module;
import cn.bytes.jtim.core.module.handler.codec.DefineCodecHandler;
import io.netty.channel.ChannelPipeline;

/**
 * 处理器管理模块
 *
 * @version 1.0
 * @date 2020/2/16 22:36
 */
public interface ChannelHandlerModule extends Module {

    void optionHandler(ChannelPipeline channelPipeline);

    ChannelHandlerModule codec(DefineCodecHandler defineCodecHandler);

    @Override
    default String key() {
        return ChannelHandlerModule.class.getSimpleName();
    }
}
