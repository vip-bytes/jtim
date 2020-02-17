package cn.bytes.jtim.core.module.client;

import cn.bytes.jtim.core.config.Configuration;
import cn.bytes.jtim.core.module.initialize.SimpleClientInitializeModule;

/**
 * @version 1.0
 * @date 2020/2/16 23:08
 */
public class NettyTcpClient extends SimpleClientInitializeModule {

    public NettyTcpClient(Configuration configuration) {
        super(configuration);
    }
//
//    @Override
//    public void initChannelPipeline(ChannelPipeline pipeline) {
//
//        pipeline.addLast(new ProtobufVarint32FrameDecoder())
//                .addLast(new ProtobufDecoder(Message.getDefaultInstance()))
//                .addLast(new ProtobufVarint32LengthFieldPrepender())
//                .addLast(new ProtobufEncoder())
//                .addLast(new IdleStateHandler(getConfiguration().getHeartReadTime(), 0, 0, TimeUnit.SECONDS));
//
//    }
}
