package cn.bytes.jtim.core.channel.module.handler;

import cn.bytes.jtim.core.channel.module.AbstractSimpleModule;
import cn.bytes.jtim.core.channel.module.Module;
import cn.bytes.jtim.core.channel.module.handler.codec.DefineCodecHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelPipeline;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Objects;

/**
 * @version 1.0
 * @date 2020/2/16 22:37
 */
@Slf4j
@Getter
public abstract class SimpleChannelHandlerModule extends AbstractSimpleModule implements ChannelHandlerModule {

    private final Deque<DefineCodecHandler> defineCodecHandlers = new LinkedList<>();

    public SimpleChannelHandlerModule() {
    }

    @Override
    public void optionHandler(ChannelPipeline channelPipeline) {
        this.optionHandler0(channelPipeline);
        channelPipeline.addLast(this.defineCodecHandlers.toArray(new ChannelHandler[]{}));
    }

    @Override
    public ChannelHandlerModule codec(DefineCodecHandler defineCodecHandler) {
        if (Objects.nonNull(defineCodecHandler)) {
            log.info("添加codec处理器: {}", defineCodecHandler);
            defineCodecHandler.configuration(getConfiguration());
            defineCodecHandler.host(this);
            defineCodecHandlers.addLast(defineCodecHandler);
        }
        return this;
    }

    private void refresh() {
        Module module = getHost();
        if (Objects.isNull(module)) {
            return;
        }
        this.putAll(module);
    }

    public abstract void optionHandler0(ChannelPipeline channelPipeline);
}
