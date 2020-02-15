package cn.bytes.jtim.core.handler;

import cn.bytes.jtim.core.DefineManagerInitialize;
import cn.bytes.jtim.core.protocol.protobuf.Message;
import io.netty.handler.codec.MessageToMessageEncoder;
import lombok.Getter;

/**
 * @version 1.0
 * @date 2020/2/14 23:04
 */
@Getter
public abstract class AbstractSimpleChannelOutboundHandler extends MessageToMessageEncoder<Message>
        implements DefineChannelHandler {

    private DefineManagerInitialize defineManagerInitialize;

    @Override
    public void bindManagerInitialize(DefineManagerInitialize defineManagerInitialize) {
        this.defineManagerInitialize = defineManagerInitialize;
    }
}
