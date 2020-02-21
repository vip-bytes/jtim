package cn.bytes.jtim.core.module.handler.codec;

import cn.bytes.jtim.core.config.Configuration;
import cn.bytes.jtim.core.module.Module;
import io.netty.channel.ChannelHandler;
import io.netty.handler.codec.MessageToMessageEncoder;

/**
 * @version 1.0
 * @date 2020/2/14 23:04
 */
@ChannelHandler.Sharable
public abstract class AbstractSimpleCodecOutboundHandler<T>
        extends MessageToMessageEncoder<T> implements DefineCodecHandler {

    private Module host;

    private Configuration configuration;

    @Override
    public void host(Module host) {
        this.host = host;
    }

    @Override
    public void configuration(Configuration configuration) {
        this.configuration = configuration;
    }

    public <T extends Module> T getHost() {
        return (T) host;
    }

    public Configuration getConfiguration() {
        return this.configuration;
    }

}
