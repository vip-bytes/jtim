package cn.bytes.jtim.connector.websocket;

import cn.bytes.jtim.core.channel.module.handler.codec.AbstractSimpleCodecInboundHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

/**
 * @version 1.0
 * @date 2020/3/19 9:20
 */
@ChannelHandler.Sharable
public class WebsocketFrameCodecInboundHandler extends AbstractSimpleCodecInboundHandler<WebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, WebSocketFrame webSocketFrame) throws Exception {

    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
    }


}
