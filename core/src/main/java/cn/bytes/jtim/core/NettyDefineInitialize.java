package cn.bytes.jtim.core;

import cn.bytes.jtim.core.config.Configuration;
import cn.bytes.jtim.core.config.SocketConfig;
import cn.bytes.jtim.core.handler.DefaultDefineInitialize;
import cn.bytes.jtim.core.module.Module;
import cn.bytes.jtim.core.module.ModuleManager;
import cn.bytes.jtim.core.module.ModuleMapping;
import cn.bytes.jtim.core.retry.DefaultDefineRetryManager;
import cn.bytes.jtim.core.retry.DefineRetryManager;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.net.InetSocketAddress;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static cn.bytes.jtim.core.module.ModuleMapping.MODULE_RETRY_MANAGER;

@Slf4j
@Getter
public abstract class NettyDefineInitialize extends DefaultDefineInitialize {

    public NettyDefineInitialize(Configuration configuration, ModuleManager moduleManager) {
        super(configuration, moduleManager);
    }

    public enum State {Created, Initialized, FAILED, Completed}

    public final AtomicReference<State> state = new AtomicReference<>(State.Created);

    public EventLoopGroup bossGroup;

    public EventLoopGroup workerGroup;

    @Override
    public void initChannel(ChannelPipeline pipeline) {
        if (Objects.isNull(pipeline)) {
            return;
        }
        pipeline.addLast(new IdleStateHandler(super.getConfiguration().getHeartReadTime(), 0, 0, TimeUnit.SECONDS));
    }

    public abstract Future<Void> openAsync();

    /**
     * 初始group
     */
    public void selectEventLoopGroup() {

        if (Objects.nonNull(bossGroup) && Objects.nonNull(workerGroup)) {
            return;
        }

        if (getConfiguration().isUseLinuxNativeEpoll()) {
            bossGroup = new EpollEventLoopGroup(getConfiguration().getBossThreads());
            workerGroup = new EpollEventLoopGroup(getConfiguration().getWorkerThreads());
        } else {
            bossGroup = new NioEventLoopGroup(getConfiguration().getBossThreads());
            workerGroup = new NioEventLoopGroup(getConfiguration().getWorkerThreads());
        }
    }

    public InetSocketAddress getSocketAddress() {
        return StringUtils.isNotBlank(getConfiguration().getHost()) ?
                new InetSocketAddress(getConfiguration().getHost(), getConfiguration().getPort()) :
                new InetSocketAddress(getConfiguration().getPort());
    }

    public Class<? extends ServerChannel> getNioServerSocketChannelClass() {
        Class<? extends ServerChannel> channelClass = NioServerSocketChannel.class;
        if (getConfiguration().isUseLinuxNativeEpoll()) {
            channelClass = EpollServerSocketChannel.class;
        }
        return channelClass;
    }

    public Class<? extends Channel> getNioSocketChannelClass() {
        Class<? extends Channel> channelClass = NioSocketChannel.class;
        if (getConfiguration().isUseLinuxNativeEpoll()) {
            channelClass = EpollSocketChannel.class;
        }
        return channelClass;
    }

    /**
     * 连接options
     *
     * @param bootstrap
     */
    protected void options(ServerBootstrap bootstrap) {
        SocketConfig config = getConfiguration().getSocketConfig();
        bootstrap.childOption(ChannelOption.TCP_NODELAY, config.isNoDelay());
        if (config.getSendBufferSize() > -1) {
            bootstrap.childOption(ChannelOption.SO_SNDBUF, config.getSendBufferSize());
        }
        if (config.getReceiveBufferSize() > -1) {
            bootstrap.childOption(ChannelOption.SO_RCVBUF, config.getReceiveBufferSize());
            bootstrap.childOption(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(config.getReceiveBufferSize()));
        }
        //bootstrap.childOption(ChannelOption.SO_LINGER, config.getSoLinger());
        //bootstrap.option(ChannelOption.SO_REUSEADDR, config.isReuseAddress());
        bootstrap.option(ChannelOption.SO_BACKLOG, config.getAcceptBackLog());

        //http://blog.csdn.net/u010942020/article/details/52044809
        bootstrap.childOption(ChannelOption.WRITE_BUFFER_WATER_MARK,
                new WriteBufferWaterMark(config.getWriteBufferWaterLow(), config.getWriteBufferWaterHigh()));
        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.childOption(ChannelOption.TCP_NODELAY, true);

    }

    protected void options(Bootstrap bootstrap) {
        SocketConfig config = getConfiguration().getSocketConfig();
        if (config.getReceiveBufferSize() > -1) {
            bootstrap.option(ChannelOption.SO_RCVBUF, config.getReceiveBufferSize());
            bootstrap.option(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(config.getReceiveBufferSize()));
        }
        bootstrap.option(ChannelOption.WRITE_BUFFER_WATER_MARK,
                new WriteBufferWaterMark(config.getWriteBufferWaterLow(), config.getWriteBufferWaterHigh()));
    }

    @Override
    public void open() {
        DefineRetryManager defineRetryManager = getModuleManager().getModule(MODULE_RETRY_MANAGER);
        this.open(defineRetryManager);
    }

    @Override
    public void open(DefineRetryManager defineRetryManager) {

        this.validatorMustModule();

        this.init();

        this.openAsync().addListener((FutureListener<Void>) future -> {
            if (future.isSuccess()) {
                this.state.set(State.Completed);
                log.info(" {} at addr: {}:{}", this.getClass().getSimpleName(), getConfiguration().getHost(), getConfiguration().getPort());
                this.openSuccessAfterHandler();
            } else {
                log.error(" {} failed at addr: {}:{}", this.getClass().getSimpleName(), getConfiguration().getHost(), getConfiguration().getPort());
                this.state.set(State.FAILED);
                this.isRetry(defineRetryManager);
            }
        });
    }

    /**
     * 启动成功以后执行逻辑
     */
    protected void openSuccessAfterHandler() {
    }

    private void isRetry(DefineRetryManager retryManager) {
        if (Objects.nonNull(retryManager)) {
            retryManager.retry(retryStatus -> {
                if (DefaultDefineRetryManager.RetryStatus.CLOSE.equals(retryStatus)) {
                    this.close();
                }
                if (DefaultDefineRetryManager.RetryStatus.EXECUTE.equals(retryStatus)) {
                    this.open();
                }
            });
        }
    }

    /**
     * 重试连接
     *
     * @param defineRetryManager
     */
    private void failAfterHandler(DefineRetryManager defineRetryManager) {
        if (Objects.nonNull(defineRetryManager)) {

        }
    }

    /**
     * 验证不虚模块是否存在
     */
    private void validatorMustModule() {
        for (ModuleMapping moduleMapping : ModuleMapping.values()) {
            Module module = getModuleManager().getModule(moduleMapping);
            if (moduleMapping.isMust() && Objects.isNull(module)) {
                throw new RuntimeException(String.format("必填模块[%s]不能为空", moduleMapping.getName()));
            }
        }
    }

    @Override
    public void close() {
        if (Objects.nonNull(bossGroup)) {
            bossGroup.shutdownGracefully();
        }
        if (Objects.nonNull(workerGroup)) {
            workerGroup.shutdownGracefully();
        }
    }
}
