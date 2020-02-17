package cn.bytes.jtim.core.module.handler;

import cn.bytes.jtim.core.module.AbstractSimpleModule;
import cn.bytes.jtim.core.module.ModuleMapping;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelPipeline;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @version 1.0
 * @date 2020/2/16 22:37
 */
@Slf4j
@Getter
public abstract class SimpleChannelHandlerModule extends AbstractSimpleModule implements ChannelHandlerModule {

    private Deque<ChannelHandler> channelHandlers = new LinkedList<>();

    @Override
    public ModuleMapping mapping() {
        return ModuleMapping.MODULE_CHANNEL_HANDLER;
    }

    private void addLast(ChannelHandler channelHandler) {
        if (Objects.isNull(channelHandler)) {
            log.info("不能添加空的处理器");
            return;
        }

        if (DefineChannelHandler.class.isAssignableFrom(channelHandler.getClass())) {
            ((DefineChannelHandler) channelHandler).bindChannelHandlerModule(this);
        }
        this.channelHandlers.addLast(channelHandler);
    }

    @Override
    public ChannelHandlerModule addLast(ChannelHandler... channelHandlers) {

        if (Objects.isNull(channelHandlers)) {
            return this;
        }
        Stream.of(channelHandlers).forEach(this::addLast);
        return this;
    }

    @Override
    public void optionHandler(ChannelPipeline channelPipeline) {
        this.optionHandler0(channelPipeline);
        channelPipeline.addLast(this.channelHandlers.toArray(new ChannelHandler[]{}));
    }

    protected abstract void optionHandler0(ChannelPipeline channelPipeline);
}
