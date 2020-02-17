package cn.bytes.jtim.broker.module.handler;

import cn.bytes.jtim.core.module.handler.SimpleChannelHandlerProtoBufModule;
import io.netty.channel.ChannelPipeline;

/**
 * 自定义tcp连接处理器
 *
 * @version 1.0
 * @date 2020/2/17 22:22
 */
public class SimpleChannelHandlerWebsocketModule extends SimpleChannelHandlerProtoBufModule {
    @Override
    public void optionHandler0(ChannelPipeline channelPipeline) {
        super.optionHandler0(channelPipeline);
    }
}
