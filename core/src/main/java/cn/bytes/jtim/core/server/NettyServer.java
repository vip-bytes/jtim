package cn.bytes.jtim.core.server;

import cn.bytes.jtim.core.NettyDefineInitialize;
import cn.bytes.jtim.core.config.Configuration;
import cn.bytes.jtim.core.connection.DefineConnectionManager;
import cn.bytes.jtim.core.handler.DefineHandlerManager;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.util.concurrent.Future;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public abstract class NettyServer extends NettyDefineInitialize {

    public NettyServer(Configuration configuration, DefineHandlerManager defineHandlerManager, DefineConnectionManager defineConnectionManager) {
        super(configuration, defineHandlerManager, defineConnectionManager);
    }

    @Override
    public Future<Void> openAsync() {

        ServerBootstrap b = new ServerBootstrap();

        b.group(bossGroup, workerGroup).channel(super.getNioServerSocketChannelClass()).childHandler(this);

        super.options(b);

        return b.bind(super.getSocketAddress());
    }

    @Override
    public void init() {
        state.compareAndSet(State.Created, State.Initialized);
        super.selectEventLoopGroup();
    }

    @Override
    public void close() {
        log.info("Socket server closing");
        super.close();
        log.info("Socket server closed!!");
    }

}
