package cn.bytes.jtim.core.server;

import cn.bytes.jtim.core.connection.Connection;
import cn.bytes.jtim.core.connection.ConnectionManager;
import cn.bytes.jtim.core.connection.DefaultConnectionManager;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public abstract class InitializerServer extends ChannelInitializer<Channel> implements Server {

    /**
     * 连接管理器
     */
    private static final ConnectionManager<String, Connection> connectionManager = new DefaultConnectionManager();

}
