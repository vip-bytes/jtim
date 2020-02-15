package cn.bytes.jtim.core.handler;

import cn.bytes.jtim.core.DefineManagerInitialize;
import cn.bytes.jtim.core.config.Configuration;
import cn.bytes.jtim.core.connection.Connection;
import cn.bytes.jtim.core.connection.DefaultDefineConnectionManager;
import cn.bytes.jtim.core.connection.DefineConnectionManager;
import cn.bytes.jtim.core.protocol.protobuf.Message;
import cn.bytes.jtim.core.register.DefineRegisterManager;
import cn.bytes.jtim.core.router.DefineRouterManager;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * @version 1.0
 * @date 2020/2/14 14:44
 */
@Getter
public abstract class DefaultDefineInitialize
        extends ChannelInitializer<Channel>
        implements DefineManagerInitialize {

    private Configuration configuration;

    private DefineHandlerManager defineHandlerManager;

    private DefineConnectionManager defineConnectionManager;

    private DefineRegisterManager defineRegisterManager;

    private DefineRouterManager defineRouterManager;

    public DefaultDefineInitialize(Configuration configuration) {
        this(configuration, null, null, null, null);
    }

    public DefaultDefineInitialize(Configuration configuration,
                                   DefineHandlerManager defineHandlerManager,
                                   DefineConnectionManager defineConnectionManager) {
        this(configuration, defineHandlerManager, defineConnectionManager, null, null);
    }

    public DefaultDefineInitialize(Configuration configuration,
                                   DefineHandlerManager defineHandlerManager,
                                   DefineConnectionManager defineConnectionManager,
                                   DefineRegisterManager defineRegisterManager,
                                   DefineRouterManager defineRouterManager) {
        this.configuration = configuration;
        this.defineHandlerManager = defineHandlerManager;
        this.defineConnectionManager = defineConnectionManager;
        this.defineRegisterManager = defineRegisterManager;
        this.defineRouterManager = defineRouterManager;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {

        final ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new ProtobufVarint32FrameDecoder());
        pipeline.addLast(new ProtobufDecoder(Message.getDefaultInstance()));
        pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
        pipeline.addLast(new ProtobufEncoder());

        if (Objects.nonNull(defineHandlerManager)) {
            this.initChannel(defineHandlerManager);
            if (!defineHandlerManager.isEmpty()) {
                defineHandlerManager.forEach(defineChannelHandler -> {
                    defineChannelHandler.bindManagerInitialize(this);
                    pipeline.addLast(defineChannelHandler);
                });
            }
        }

        this.initChannel(pipeline);
    }

    /**
     * 这种方式注册的处理器，可以拿到初始信息
     *
     * @param defineHandlerManager
     */
    public void initChannel(DefineHandlerManager defineHandlerManager) {
    }

    /**
     * 原始的注册，没有关联初始信息
     *
     * @param pipeline
     */
    public void initChannel(ChannelPipeline pipeline) {
    }

    @Override
    public DefineManagerInitialize use(DefineRegisterManager defineRegisterManager) {
        this.defineRegisterManager = defineRegisterManager;
        return this;
    }

    @Override
    public DefineManagerInitialize use(DefineHandlerManager defineHandlerManager) {
        this.defineHandlerManager = defineHandlerManager;
        return this;
    }

    @Override
    public DefineManagerInitialize use(DefineRouterManager defineRouterManager) {
        this.defineRouterManager = defineRouterManager;
        return this;
    }

    @Override
    public DefineManagerInitialize use(DefineConnectionManager defineConnectionManager) {
        this.defineConnectionManager = defineConnectionManager;
        return this;
    }
}
