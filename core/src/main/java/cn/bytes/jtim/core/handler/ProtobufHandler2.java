package cn.bytes.jtim.core.handler;

import cn.bytes.jtim.core.Actuator;
import cn.bytes.jtim.core.protocol.protobuf.HeartbeatRequest;
import cn.bytes.jtim.core.protocol.protobuf.HeartbeatResponse;
import cn.bytes.jtim.core.protocol.protobuf.Message;
import com.google.protobuf.ByteString;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @version 1.0
 * @date 2020/2/13 18:14
 */
@Slf4j
@ChannelHandler.Sharable
public class ProtobufHandler2 extends SimpleChannelInboundHandler<Message> {

    private Actuator actuator;

    public ProtobufHandler2(Actuator actuator) {
        this.actuator = actuator;
    }

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
        ctx.close();
        //actuator.close();
        //客户端连接断开了直接断开连接
        //尝试重连,轮询选址等 TODO
        actuator.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        cause.printStackTrace();
    }
}
