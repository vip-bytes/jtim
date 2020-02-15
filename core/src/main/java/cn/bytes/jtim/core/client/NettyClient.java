package cn.bytes.jtim.core.client;

import cn.bytes.jtim.core.NettyDefineInitialize;
import cn.bytes.jtim.core.config.Configuration;
import cn.bytes.jtim.core.connection.DefineConnectionManager;
import cn.bytes.jtim.core.handler.DefineHandlerManager;
import cn.bytes.jtim.core.module.Module;
import cn.bytes.jtim.core.module.ModuleManager;
import io.netty.bootstrap.Bootstrap;
import io.netty.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;

/**
 * @author maliang@sioniov.com
 * @version 1.0
 * @date 2020/2/10 23:11
 */
@Slf4j
public abstract class NettyClient extends NettyDefineInitialize {

    public NettyClient(Configuration configuration, ModuleManager moduleManager) {
        super(configuration, moduleManager);
    }

    @Override
    public Future<Void> openAsync() {

        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(bossGroup).channel(getNioSocketChannelClass()).handler(this);

        super.options(bootstrap);

        return bootstrap.connect(super.getSocketAddress());
    }

    @Override
    public void init() {
        state.compareAndSet(State.Created, State.Initialized);
        super.selectEventLoopGroup();
    }

    @Override
    public void close() {
        log.info("Socket connection closing!!!");
        super.close();
        log.info("Socket connection closed");
    }


}
