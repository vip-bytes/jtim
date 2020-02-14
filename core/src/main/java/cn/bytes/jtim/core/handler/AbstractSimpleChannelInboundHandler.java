package cn.bytes.jtim.core.handler;

import cn.bytes.jtim.core.Actuator;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.Getter;

/**
 * @version 1.0
 * @date 2020/2/14 15:29
 */
@Getter
public abstract class AbstractSimpleChannelInboundHandler<T> extends SimpleChannelInboundHandler<T> {

    private Actuator actuator;

    public AbstractSimpleChannelInboundHandler(Actuator actuator) {
        this.actuator = actuator;
    }

}
