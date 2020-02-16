package cn.bytes.jtim.core.module.handler;

import cn.bytes.jtim.core.module.initialize.InitializeModule;
import cn.bytes.jtim.core.protocol.protobuf.Message;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 * @date 2020/2/16 23:26
 */
@Slf4j
public class SimpleChannelHandlerProtoBufModule extends SimpleChannelHandlerModule {

    public SimpleChannelHandlerProtoBufModule() {
        InitializeModule initializeModule = getHost();
        final int heartbeat = Objects.isNull(initializeModule) ? 30 : Objects.isNull(initializeModule.getConfiguration()) ?
                30 : initializeModule.getConfiguration().getHeartReadTime();

        this.addLast(new ProtobufVarint32FrameDecoder())
                .addLast(new ProtobufDecoder(Message.getDefaultInstance()))
                .addLast(new ProtobufVarint32LengthFieldPrepender())
                .addLast(new ProtobufEncoder())
                .addLast(new IdleStateHandler(heartbeat, 0, 0, TimeUnit.SECONDS));
        log.info("初始 Protobuf 处理器...");
    }
}
