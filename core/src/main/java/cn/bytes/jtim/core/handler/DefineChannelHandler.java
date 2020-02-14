package cn.bytes.jtim.core.handler;

import cn.bytes.jtim.core.Actuator;
import io.netty.channel.ChannelHandler;

public interface DefineChannelHandler<I> extends ChannelHandler {

    void bindActuator(Actuator actuator);
}
