package cn.bytes.jtim.connector.websocket;

import cn.bytes.jtim.core.channel.module.connection.Connection;
import cn.bytes.jtim.core.channel.module.connection.ConnectionModule;
import cn.bytes.jtim.core.channel.module.handler.codec.AbstractSimpleCodecInboundHandler;
import cn.bytes.jtim.core.constant.DefineConstant;
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

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        boolean decoderResult = request.decoderResult().isSuccess();
        if (!decoderResult) {
            sendHttpResponse(ctx, request, new DefaultFullHttpResponse(HTTP_1_1, BAD_REQUEST));
            return;
        }

        // method  = get,post
        HttpMethod method = request.method();
        if (!method.equals(HttpMethod.GET) && !method.equals(HttpMethod.POST)) {
            sendHttpResponse(ctx, request, new DefaultFullHttpResponse(HTTP_1_1, FORBIDDEN));
            return;
        }

        //token 验证
        final String token = getParam(request, DefineConstant.TOKEN);
        if (StringUtils.isBlank(token)) {
            sendHttpResponse(ctx, request, new DefaultFullHttpResponse(HTTP_1_1, UNAUTHORIZED));
            return;
        }

        // TODO: 2020/3/19 验证对于的token信息
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(getWebSocketLocation(request), null, true);
        WebSocketServerHandshaker webSocketServerHandshaker = wsFactory.newHandshaker(request);

        final Channel channel = ctx.channel();

        if (Objects.isNull(webSocketServerHandshaker)) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(channel);
        } else {
            ChannelFuture channelFuture = webSocketServerHandshaker.handshake(channel, request);

            if (channelFuture.isSuccess()) {
                // TODO: 2020/3/20  完善信息
                Connection connection = Connection.builder()
                        .channel(channel)
                        .channelId(channel.id().asLongText())
                        .clientTime(System.currentTimeMillis())
                        .token(token)
                        .source(getParam(request, DefineConstant.SOURCE))
                        .build();

                channel.attr(DefineConstant.ATTRIBUTE_CONNECTION).set(connection);
                log.info("认证成功 {}", connection);
                ConnectionModule connectionModule = this.getHost().getModule(ConnectionModule.class);
                connectionModule.saveConnection(connection);
            } else {
                log.info("认证失败 {}", ctx);
                ctx.close();
            }
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        final Channel channel = ctx.channel();
        ConnectionModule connectionModule = this.getHost().getModule(ConnectionModule.class);
        Connection connection = channel.attr(DefineConstant.ATTRIBUTE_CONNECTION).get();
        connectionModule.removeConnection(connection);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("连接成功 {}", ctx);
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
        String location = request.headers().get(HttpHeaderNames.HOST) + DefineConstant.WEBSOCKET_PATH;
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
