package cn.bytes.jtim.core.server;

import cn.bytes.jtim.core.config.Configuration;
import io.netty.channel.ChannelPipeline;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyTcpServer extends NettyServer {

    public NettyTcpServer(Configuration configuration) {
        super(configuration);
    }

    @Override
    public void init() {
        super.init();
        //初始化信息，构建编码解码器
    }

    @Override
    public void handlerOptions(ChannelPipeline pipeline) {
//        pipeline.addLast(PACKET_DECODER, this.packetToMessageDecoder);
//        pipeline.addLast(PACKET_ENCODER, this.messageToPacketEncoder);
//        pipeline.addLast(PACKET_HANDLER, this.serverChannelHandlerAdapter);
    }


}
