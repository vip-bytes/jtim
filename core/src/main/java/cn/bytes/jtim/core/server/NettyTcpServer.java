package cn.bytes.jtim.core.server;

import cn.bytes.jtim.core.config.Configuration;
import cn.bytes.jtim.core.handler.ProtobufHandler;
import cn.bytes.jtim.core.protocol.protobuf.Message;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyTcpServer extends NettyServer {

    public NettyTcpServer(Configuration configuration) {
        super(configuration);
    }

    @Override
    public void channelPipelineOptions(ChannelPipeline pipeline) {

        //解码器，通过Google Protocol Buffers序列化框架动态的切割接收到的ByteBuf
        pipeline.addLast(new ProtobufVarint32FrameDecoder());
        //服务器端接收的是客户端RequestUser对象，所以这边将接收对象进行解码生产实列
        pipeline.addLast(new ProtobufDecoder(Message.getDefaultInstance()));
        //Google Protocol Buffers编码器
        pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
        //Google Protocol Buffers编码器
        pipeline.addLast(new ProtobufEncoder());

        pipeline.addLast(new ProtobufHandler());

    }

    /**
     * public class ProtoServerHandler extends SimpleChannelInboundHandler<DataInfo.RequestUser> {
     *
     *     @Override
     *     protected void channelRead0(ChannelHandlerContext ctx, DataInfo.RequestUser msg) throws Exception {
     *         System.out.println(msg.getUserName());
     *         System.out.println(msg.getAge());
     *         System.out.println(msg.getPassword());
     *
     *         DataInfo.ResponseBank bank = DataInfo.ResponseBank.newBuilder().setBankName("中国工商银行")
     *                 .setBankNo("6222222200000000000").setMoney(560000.23).build();
     *
     *         ctx.channel().writeAndFlush(bank);
     *     }
     * }
     */

}
