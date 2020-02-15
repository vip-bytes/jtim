package cn.bytes.jtim.core.server;

import cn.bytes.jtim.core.config.Configuration;
import cn.bytes.jtim.core.handler.DefineHandlerManager;
import cn.bytes.jtim.core.module.ModuleManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyTcpServer extends NettyServer {

    public NettyTcpServer(Configuration configuration, ModuleManager moduleManager) {
        super(configuration, moduleManager);
    }

    @Override
    public void initChannel(DefineHandlerManager defineHandlerManager) {

//        defineHandlerManager.addHandlerLast()
    }


}
