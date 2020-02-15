package cn.bytes.jtim.core.server;

import cn.bytes.jtim.core.NettyDefineInitialize;
import cn.bytes.jtim.core.config.Configuration;
import cn.bytes.jtim.core.module.ModuleManager;
import cn.bytes.jtim.core.module.ModuleMapping;
import cn.bytes.jtim.core.register.DefineRegisterManager;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.util.concurrent.Future;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@Getter
public abstract class NettyServer extends NettyDefineInitialize {

    public NettyServer(Configuration configuration, ModuleManager moduleManager) {
        super(configuration, moduleManager);
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

    @Override
    protected void openSuccessAfterHandler() {
        DefineRegisterManager defineRegisterManager =
                getModuleManager().getModule(ModuleMapping.MODULE_REGISTER_MANAGER);
        if (Objects.nonNull(defineRegisterManager)) {
            defineRegisterManager.register(getRegisterNode());
        }
    }

    protected DefineRegisterManager.RegisterNode getRegisterNode() {
        return DefineRegisterManager.RegisterNode.builder()
                .key(null)
                .host(getConfiguration().getHost())
                .port(getConfiguration().getPort())
                .build();
    }

}
