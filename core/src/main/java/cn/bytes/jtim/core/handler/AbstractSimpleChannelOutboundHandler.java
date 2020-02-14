package cn.bytes.jtim.core.handler;

import cn.bytes.jtim.core.Actuator;
import io.netty.handler.codec.MessageToMessageEncoder;
import lombok.Getter;

/**
 * @version 1.0
 * @date 2020/2/14 23:04
 */
@Getter
public abstract class AbstractSimpleChannelOutboundHandler<I> extends MessageToMessageEncoder<I>
        implements DefineChannelHandler<I> {

    private Actuator actuator;

    @Override
    public void bindActuator(Actuator actuator) {
        this.actuator = actuator;
    }

}
