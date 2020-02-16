package cn.bytes.jtim.test;

import cn.bytes.jtim.core.module.handler.AbstractSimpleChannelInboundHandler;
import cn.bytes.jtim.core.module.initialize.InitializeModule;
import cn.bytes.jtim.core.module.retry.SimpleRetryModule;
import cn.bytes.jtim.core.protocol.protobuf.HeartbeatResponse;
import cn.bytes.jtim.core.protocol.protobuf.Message;
import com.google.protobuf.ByteString;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @version 1.0
 * @date 2020/2/13 18:14
 */
@Slf4j
@ChannelHandler.Sharable
public class ProtobufClientHandler extends AbstractSimpleChannelInboundHandler<Message> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message message) throws Exception {

        log.info("【客户端】消息: {}", message);

        Message.Cmd cmd = message.getCmd();
        if (Message.Cmd.HeartbeatRequest.equals(cmd)) {
            channelHandlerContext.writeAndFlush(Message.newBuilder()
                    .setCmd(Message.Cmd.HeartbeatResponse)
                    .setHeartbeatResponse(HeartbeatResponse.newBuilder()
                            .setPong(ByteString.EMPTY)
                            .build())
                    .build());
        }

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("【客户端】连接断开:{}", ctx);

        InitializeModule initializeModule = getChannelHandlerModule().getHost();
        initializeModule.open(SimpleRetryModule.builder()
                .retryMax(new AtomicInteger(Integer.MAX_VALUE))
                .build());
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("连接异常: ", cause);
        ctx.close();
    }
}
