package cn.bytes.jtim.http.server.module.codec;

import cn.bytes.jtim.core.module.connection.Connection;
import cn.bytes.jtim.core.module.connection.ConnectionModule;
import cn.bytes.jtim.core.module.handler.codec.AbstractSimpleCodecInboundHandler;
import cn.bytes.jtim.core.protocol.protobuf.Message;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Objects;

/**
 * @version 1.0
 * @date 2020/2/13 18:14
 */
@Slf4j
@ChannelHandler.Sharable
public class ProtobufClientCodec extends AbstractSimpleCodecInboundHandler<Message> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message message) throws Exception {
        log.info("【客户端】消息: {}", message);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ConnectionModule connectionModule = getHost().getModule(ConnectionModule.class);
        if (Objects.nonNull(connectionModule)) {
            connectionModule
                    .removeConnection(ctx.channel());
            log.info("连接断开,当前连接数量:{}", connectionModule.onLine());
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("连接成功");
        Channel channel = ctx.channel();
        InetSocketAddress socketAddress = (InetSocketAddress) channel.remoteAddress();
        Connection connection = Connection.builder()
                .channel(channel)
                .remotePort(socketAddress.getPort())
                .remoteHost(socketAddress.getAddress().getHostAddress())
                .channelId(channel.id().asLongText())
                .clientTime(System.currentTimeMillis())
                .build();
        ConnectionModule connectionModule = getHost().getModule(ConnectionModule.class);
        if (Objects.nonNull(connectionModule)) {
            connectionModule
                    .saveConnection(connection);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
