package cn.bytes.jtim.core.handler;

import cn.bytes.jtim.core.protocol.protobuf.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author maliang@sioniov.com
 * @version 1.0
 * @date 2020/2/13 18:14
 */
public class ProtobufHandler extends SimpleChannelInboundHandler<Message> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message message) throws Exception {

    }

}
