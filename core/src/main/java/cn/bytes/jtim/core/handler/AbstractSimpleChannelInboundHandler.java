package cn.bytes.jtim.core.handler;

import cn.bytes.jtim.core.DefineManagerInitialize;
import cn.bytes.jtim.core.connection.DefineConnectionManager;
import cn.bytes.jtim.core.protocol.protobuf.Message;
import cn.bytes.jtim.core.register.DefineRegisterManager;
import cn.bytes.jtim.core.router.DefineRouterManager;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.Getter;

import java.util.Objects;

/**
 * @version 1.0
 * @date 2020/2/14 15:29
 */
@Getter
public abstract class AbstractSimpleChannelInboundHandler extends SimpleChannelInboundHandler<Message>
        implements DefineChannelHandler {

    private DefineManagerInitialize defineManagerInitialize;

    @Override
    public void bindManagerInitialize(DefineManagerInitialize defineManagerInitialize) {
        this.defineManagerInitialize = defineManagerInitialize;
    }

}
