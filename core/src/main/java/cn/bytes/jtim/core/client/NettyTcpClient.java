package cn.bytes.jtim.core.client;

import cn.bytes.jtim.core.config.Configuration;
import cn.bytes.jtim.core.handler.DefineHandlerManager;
import cn.bytes.jtim.core.handler.ProtobufTcpClientHandler;
import cn.bytes.jtim.core.module.ModuleManager;
import lombok.extern.slf4j.Slf4j;

/**
 * @version 1.0
 * @date 2020/2/10 23:10
 */
@Slf4j
public class NettyTcpClient extends NettyClient {

    public NettyTcpClient(Configuration configuration, ModuleManager moduleManager) {
        super(configuration, moduleManager);
    }

    @Override
    public void initChannel(DefineHandlerManager defineHandlerManager) {
        defineHandlerManager.addHandlerLast(new ProtobufTcpClientHandler());
    }

}
