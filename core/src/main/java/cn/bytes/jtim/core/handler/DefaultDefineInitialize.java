package cn.bytes.jtim.core.handler;

import cn.bytes.jtim.core.DefineManagerInitialize;
import cn.bytes.jtim.core.config.Configuration;
import cn.bytes.jtim.core.connection.DefineConnectionManager;
import cn.bytes.jtim.core.module.DefaultModuleManager;
import cn.bytes.jtim.core.module.Module;
import cn.bytes.jtim.core.module.ModuleManager;
import cn.bytes.jtim.core.module.ModuleMapping;
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
import lombok.Getter;

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

    private ModuleManager moduleManager;

    public DefaultDefineInitialize(Configuration configuration, ModuleManager moduleManager) {
        this.configuration = configuration;
        this.moduleManager = moduleManager;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {

        final ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new ProtobufVarint32FrameDecoder());
        pipeline.addLast(new ProtobufDecoder(Message.getDefaultInstance()));
        pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
        pipeline.addLast(new ProtobufEncoder());

        DefineHandlerManager defineHandlerManager = moduleManager.getModule(ModuleMapping.MODULE_HANDLER_MANAGER);

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

}
