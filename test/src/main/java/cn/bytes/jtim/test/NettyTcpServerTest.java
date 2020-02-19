package cn.bytes.jtim.test;

import cn.bytes.jtim.core.config.Configuration;
import cn.bytes.jtim.core.module.connection.SimpleConnectionModule;
import cn.bytes.jtim.core.module.handler.SimpleChannelHandlerProtoBufModule;
import cn.bytes.jtim.core.module.server.NettyTcpServer;

/**
 * @version 1.0
 * @date 2020/2/13 22:16
 */
public class NettyTcpServerTest {

    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.setHost("127.0.0.1");
        configuration.setPort(1999);

        NettyTcpServer nettyTcpServer = new NettyTcpServer(configuration);

        //当前host没有往上级查找
        nettyTcpServer
                .then(new SimpleChannelHandlerProtoBufModule()
                        .codec(new ProtobufServerHandler()))
                .then(new SimpleConnectionModule());
        nettyTcpServer.open();
    }
}
