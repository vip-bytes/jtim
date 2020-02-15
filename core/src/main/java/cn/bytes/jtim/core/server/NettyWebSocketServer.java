package cn.bytes.jtim.core.server;

import cn.bytes.jtim.core.config.Configuration;
import cn.bytes.jtim.core.connection.DefineConnectionManager;
import cn.bytes.jtim.core.handler.DefineHandlerManager;
import cn.bytes.jtim.core.module.Module;
import cn.bytes.jtim.core.module.ModuleManager;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;

/**
 * @version 1.0
 * @date 2020/2/10 22:58
 */
public class NettyWebSocketServer extends NettyServer {
    public NettyWebSocketServer(Configuration configuration, ModuleManager moduleManager) {
        super(configuration, moduleManager);
    }


//    @Override
//    public void channelHandlerOptions(ChannelPipeline pipeline) {
//        pipeline.addLast(new HttpServerCodec());
//        pipeline.addLast(new HttpObjectAggregator(this.configuration.getMaxHttpContentLength()));
//        pipeline.addLast(new WebSocketServerCompressionHandler());
//        pipeline.addLast(new WebSocketServerProtocolHandler("/ws", null, true, this.configuration.getMaxWebsocketFrameSize()));
////        pipeline.addLast(this.wsServerChannelHandlerAdapter);
//    }

}
