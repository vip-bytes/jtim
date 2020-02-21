package cn.bytes.jtim.core.module.handler.codec;

import cn.bytes.jtim.core.config.Configuration;
import cn.bytes.jtim.core.module.Module;
import io.netty.channel.ChannelHandler;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @version 1.0
 * @date 2020/2/14 15:29
 */
@ChannelHandler.Sharable
public abstract class AbstractSimpleCodecInboundHandler<T>
        extends SimpleChannelInboundHandler<T> implements DefineCodecHandler {

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
