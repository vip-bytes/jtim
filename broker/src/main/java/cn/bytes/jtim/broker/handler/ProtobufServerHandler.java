package cn.bytes.jtim.broker.handler;

import cn.bytes.jtim.core.connection.Connection;
import cn.bytes.jtim.core.handler.AbstractSimpleChannelInboundHandler;
import cn.bytes.jtim.core.protocol.protobuf.HeartbeatRequest;
import cn.bytes.jtim.core.protocol.protobuf.HeartbeatResponse;
import cn.bytes.jtim.core.protocol.protobuf.Message;
import com.google.protobuf.ByteString;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 * @date 2020/2/13 18:14
 */
@Slf4j
@ChannelHandler.Sharable
public class ProtobufServerHandler extends AbstractSimpleChannelInboundHandler {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message message) throws Exception {

        log.info("服务端收到消息: {}", message);
        Message.Cmd cmd = message.getCmd();
        TimeUnit.MILLISECONDS.sleep(1);
        if (Message.Cmd.HeartbeatRequest.equals(cmd)) {
            channelHandlerContext.writeAndFlush(Message.newBuilder()
                    .setCmd(Message.Cmd.HeartbeatResponse)
                    .setHeartbeatResponse(HeartbeatResponse.newBuilder()
                            .setPong(ByteString.EMPTY)
                            .build())
                    .build());
        }

        if (Message.Cmd.HeartbeatResponse.equals(cmd)) {
            channelHandlerContext.writeAndFlush(Message.newBuilder()
                    .setCmd(Message.Cmd.HeartbeatRequest)
                    .setHeartbeatRequest(HeartbeatRequest.newBuilder()
                            .setPing(ByteString.EMPTY)
                            .build())
                    .build());
        }

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("连接成功:{}", ctx);

        Channel channel = ctx.channel();
        Connection connection = Connection.builder()
                .channel(channel)
                .channelId(channel.id().asLongText())
                .clientTime(System.currentTimeMillis())
                .build();

        Message message = Message.newBuilder().setCmd(Message.Cmd.HeartbeatRequest)
                .setHeartbeatRequest(HeartbeatRequest.newBuilder()
                        .setPing(ByteString.EMPTY)
                        .build()).build();

        getDefineConnectionManager()
                .saveConnection(connection)
                .writeAndFlush(connection, message);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        getDefineConnectionManager()
                .removeConnection(ctx.channel());
    }
}
