package cn.bytes.jtim.core.server;

import cn.bytes.jtim.core.config.Configuration;
import cn.bytes.jtim.core.connection.DefineConnectionManager;
import cn.bytes.jtim.core.handler.DefineHandlerManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyTcpServer extends NettyServer {

    public NettyTcpServer(Configuration configuration, DefineHandlerManager defineHandlerManager, DefineConnectionManager defineConnectionManager) {
        super(configuration, defineHandlerManager, defineConnectionManager);
    }

    @Override
    public void initChannel(DefineHandlerManager defineHandlerManager) {

//        defineHandlerManager.addHandlerLast()
    }


}
