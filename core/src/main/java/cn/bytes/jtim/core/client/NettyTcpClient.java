package cn.bytes.jtim.core.client;

import cn.bytes.jtim.core.config.Configuration;
import cn.bytes.jtim.core.connection.DefineConnectionManager;
import cn.bytes.jtim.core.handler.DefineHandlerManager;
import cn.bytes.jtim.core.handler.ProtobufTcpClientHandler;
import cn.bytes.jtim.core.protocol.protobuf.Message;
import io.netty.channel.ChannelPipeline;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * @version 1.0
 * @date 2020/2/10 23:10
 */
@Slf4j
public class NettyTcpClient extends NettyClient {

    public NettyTcpClient(Configuration configuration, DefineHandlerManager defineHandlerManager, DefineConnectionManager defineConnectionManager) {
        super(configuration, defineHandlerManager, defineConnectionManager);
    }

    @Override
    public void initChannel(DefineHandlerManager defineHandlerManager) {
        defineHandlerManager.addHandlerLast(new ProtobufTcpClientHandler());
    }

}
