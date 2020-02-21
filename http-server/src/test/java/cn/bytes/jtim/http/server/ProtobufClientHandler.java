package cn.bytes.jtim.http.server;

import cn.bytes.jtim.core.module.connection.Connection;
import cn.bytes.jtim.core.module.connection.ConnectionModule;
import cn.bytes.jtim.core.module.handler.codec.AbstractSimpleCodecInboundHandler;
import cn.bytes.jtim.core.module.initialize.InitializeModule;
import cn.bytes.jtim.core.module.retry.SimpleRetryModule;
import cn.bytes.jtim.core.protocol.protobuf.Message;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @version 1.0
 * @date 2020/2/13 18:14
 */
@Slf4j
@ChannelHandler.Sharable
public class ProtobufClientHandler extends AbstractSimpleCodecInboundHandler<Message> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message message) throws Exception {
        log.info("【客户端】消息: {}", message);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("【客户端】连接断开:{}", ctx);
        getHost().getModule(InitializeModule.class).open(SimpleRetryModule.builder()
                .retryMax(new AtomicInteger(Integer.MAX_VALUE))
                .build());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("连接成功");
        Channel channel = ctx.channel();
        InetSocketAddress socketAddress = (InetSocketAddress) channel.remoteAddress();
        Connection connection = Connection.builder()
                .channel(channel)
                .channelId(channel.id().asLongText())
                .remoteHost(socketAddress.getAddress().getHostAddress())
                .remotePort(socketAddress.getPort())
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
