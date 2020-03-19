package cn.bytes.jtim.connector.websocket;

import cn.bytes.jtim.core.channel.module.handler.codec.AbstractSimpleCodecInboundHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @version 1.0
 * @date 2020/3/19 13:55
 */
@Slf4j
public class WebsocketHttpRequestCodecInboundHandler extends AbstractSimpleCodecInboundHandler<FullHttpRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest) throws Exception {
        boolean decoderResult = fullHttpRequest.decoderResult().isSuccess();
        if (!decoderResult) {
            return;
        }
    }

    private static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse response) {

        final int status = response.status().code();

        if (status != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(response.status().toString(), CharsetUtil.UTF_8);
            response.content().writeBytes(buf);
            buf.release();
            HttpUtil.setContentLength(response, response.content().readableBytes());
        }

        ChannelFuture f = ctx.channel().writeAndFlush(response);
        if (!HttpUtil.isKeepAlive(req) || status != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }


}
