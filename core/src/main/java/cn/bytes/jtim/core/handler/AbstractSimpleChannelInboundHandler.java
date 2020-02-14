package cn.bytes.jtim.core.handler;

import cn.bytes.jtim.core.Actuator;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @version 1.0
 * @date 2020/2/14 15:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractSimpleChannelInboundHandler<T> extends SimpleChannelInboundHandler<T> {

    private Actuator actuator;

}
