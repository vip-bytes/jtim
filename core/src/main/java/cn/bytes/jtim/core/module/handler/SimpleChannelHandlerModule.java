package cn.bytes.jtim.core.module.handler;

import cn.bytes.jtim.core.module.AbstractSimpleModule;
import cn.bytes.jtim.core.module.ModuleMapping;
import io.netty.channel.ChannelHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Objects;

/**
 * @version 1.0
 * @date 2020/2/16 22:37
 */
@Slf4j
public class SimpleChannelHandlerModule extends AbstractSimpleModule implements ChannelHandlerModule {

    private final Deque<ChannelHandler> channelHandlers = new LinkedList<>();

    @Override
    public ModuleMapping mapping() {
        return ModuleMapping.MODULE_CHANNEL_HANDLER;
    }

    @Override
    public ChannelHandlerModule addLast(ChannelHandler channelHandler) {

        if (Objects.isNull(channelHandler)) {
            log.warn("不能添加空的处理器");
            return this;
        }

        //绑定当前容器
        if (DefineChannelHandler.class.isAssignableFrom(channelHandler.getClass())) {
            ((DefineChannelHandler) channelHandler).bindChannelHandlerModule(this);
        }

        channelHandlers.addLast(channelHandler);
        return this;
    }

    @Override
    public ChannelHandlerModule remove(ChannelHandler channelHandler) {
        channelHandlers.remove(channelHandler);
        return this;
    }

    @Override
    public ChannelHandler[] getChannelHandlers() {
        return this.channelHandlers.toArray(new ChannelHandler[]{});
    }
}
