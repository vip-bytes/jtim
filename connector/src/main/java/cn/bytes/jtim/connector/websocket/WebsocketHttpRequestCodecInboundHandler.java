package cn.bytes.jtim.connector.websocket;

import cn.bytes.jtim.core.channel.module.connection.Connection;
import cn.bytes.jtim.core.channel.module.connection.ConnectionModule;
import cn.bytes.jtim.core.channel.module.handler.codec.AbstractSimpleCodecInboundHandler;
import com.google.common.collect.Lists;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @version 1.0
 * @date 2020/3/19 13:55
 */
@Slf4j
public class WebsocketHttpRequestCodecInboundHandler extends AbstractSimpleCodecInboundHandler<FullHttpRequest> {

    private static final String TOKEN = "token";

    private static final String SOURCE = "source";

    public static final String WEBSOCKET_PATH = "/ws";

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpRequest request) throws Exception {
        boolean decoderResult = request.decoderResult().isSuccess();
        if (!decoderResult) {
            sendHttpResponse(channelHandlerContext, request, new DefaultFullHttpResponse(HTTP_1_1, BAD_REQUEST));
            return;
        }

        // method  = get,post
        HttpMethod method = request.method();
        if (!method.equals(HttpMethod.GET) && !method.equals(HttpMethod.POST)) {
            sendHttpResponse(channelHandlerContext, request, new DefaultFullHttpResponse(HTTP_1_1, FORBIDDEN));
            return;
        }

        //token 验证
        final String token = getParam(request, TOKEN);
        if (StringUtils.isBlank(token)) {
            sendHttpResponse(channelHandlerContext, request, new DefaultFullHttpResponse(HTTP_1_1, UNAUTHORIZED));
            return;
        }

        // TODO: 2020/3/19 验证对于的token信息
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(getWebSocketLocation(request), null, true);
        WebSocketServerHandshaker webSocketServerHandshaker = wsFactory.newHandshaker(request);
        if (Objects.isNull(webSocketServerHandshaker)) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(channelHandlerContext.channel());
        } else {
            ChannelFuture channelFuture = webSocketServerHandshaker.handshake(channelHandlerContext.channel(), request);

            final Channel channel = channelHandlerContext.channel();
            if (channelFuture.isSuccess()) {
                log.info("[{}]连接握手成功!", channel);

                // TODO: 2020/3/20  完善信息
                ConnectionModule connectionModule = this.getHost().getModule(ConnectionModule.class);
                connectionModule.saveConnection(Connection.builder()
                        .channel(channel)
                        .channelId(channel.id().asLongText())
                        .clientTime(System.currentTimeMillis())
                        .token(token)
                        .source(getParam(request, SOURCE))
                        .build());
            }
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ConnectionModule connectionModule = this.getHost().getModule(ConnectionModule.class);
        connectionModule.removeConnection(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    private String getParam(FullHttpRequest request, String key) {
        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(request.uri());
        Map<String, List<String>> params = queryStringDecoder.parameters();
        return params.getOrDefault(key, Lists.newArrayList()).get(0);
    }

    /**
     * 获取socketLocation
     *
     * @param request
     * @return
     */
    private static String getWebSocketLocation(FullHttpRequest request) {
        String location = request.headers().get(HttpHeaderNames.HOST) + WEBSOCKET_PATH;
        return "ws://" + location;
    }

    /**
     * 发送返回信息
     *
     * @param ctx
     * @param req
     * @param response
     */
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
