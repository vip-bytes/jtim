package cn.bytes.jtim.core.module.initialize;

import cn.bytes.jtim.core.config.Configuration;
import cn.bytes.jtim.core.config.SocketConfig;
import cn.bytes.jtim.core.module.AbstractSimpleModule;
import cn.bytes.jtim.core.module.handler.ChannelHandlerModule;
import cn.bytes.jtim.core.module.retry.RetryModule;
import cn.bytes.jtim.core.module.retry.SimpleRetryModule;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.net.InetSocketAddress;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 初始服务模块
 *
 * @version 1.0
 * @date 2020/2/16 22:00
 */
@Getter
@Slf4j
public abstract class SimpleInitializeModule extends AbstractSimpleModule implements InitializeModule {

    private Configuration configuration;

    private final AtomicReference<State> state = new AtomicReference<>(State.Created);

    private EventLoopGroup bossEventGroup;

    private EventLoopGroup workerEventGroup;

    private enum State {
        Created, Initialized, Failed, Completed
    }

    public SimpleInitializeModule(Configuration configuration) {
        this.configuration = configuration;
    }

    public void initializeEventLoopGroup() {
        if (configuration.isUseLinuxNativeEpoll()) {
            bossEventGroup = new EpollEventLoopGroup(getConfiguration().getBossThreads());
            workerEventGroup = new EpollEventLoopGroup(getConfiguration().getWorkerThreads());
        } else {
            bossEventGroup = new NioEventLoopGroup(getConfiguration().getBossThreads());
            workerEventGroup = new NioEventLoopGroup(getConfiguration().getWorkerThreads());
        }
    }

    public InetSocketAddress getSocketAddress() {
        return StringUtils.isNotBlank(configuration.getHost()) ?
                new InetSocketAddress(configuration.getHost(), configuration.getPort()) :
                new InetSocketAddress(configuration.getPort());
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

    protected void options(ServerBootstrap bootstrap) {
        SocketConfig config = configuration.getSocketConfig();
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
        SocketConfig config = configuration.getSocketConfig();
        if (config.getReceiveBufferSize() > -1) {
            bootstrap.option(ChannelOption.SO_RCVBUF, config.getReceiveBufferSize());
            bootstrap.option(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(config.getReceiveBufferSize()));
        }
        bootstrap.option(ChannelOption.WRITE_BUFFER_WATER_MARK,
                new WriteBufferWaterMark(config.getWriteBufferWaterLow(), config.getWriteBufferWaterHigh()));
    }

    @Override
    public void open(RetryModule retryModule) {
        this.init();
        this.openAsync(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel channel) throws Exception {
                ChannelPipeline channelPipeline = channel.pipeline();
                ChannelHandlerModule channelHandlerModule = getModule(ChannelHandlerModule.class);
                if (Objects.nonNull(channelHandlerModule)) {
                    channelHandlerModule.optionHandler(channelPipeline);
                }
            }
        }).addListener((FutureListener<Void>) future -> {
            if (future.isSuccess()) {
                this.state.set(State.Completed);
                log.info(" {} at addr: {}:{}", this.getClass().getSimpleName(), getConfiguration().getHost(), getConfiguration().getPort());
            } else {
                log.error(" {} failed at addr: {}:{}", this.getClass().getSimpleName(), getConfiguration().getHost(), getConfiguration().getPort());
                this.state.set(State.Failed);
                this.isRetry(retryModule);
            }
        });
    }

    /**
     * 断线重连
     *
     * @param retryModule
     */
    private void isRetry(RetryModule retryModule) {
        if (Objects.nonNull(retryModule)) {
            retryModule.retry(retryStatus -> {
                this.close();
                if (SimpleRetryModule.RetryStatus.EXECUTE.equals(retryStatus)) {
                    this.open(retryModule);
                }
            });
        } else {
            this.close();
        }
    }

    @Override
    public void close() {
        log.info("closing {}", this.getClass().getSimpleName());
        if (Objects.nonNull(bossEventGroup)) {
            bossEventGroup.shutdownGracefully();
        }
        if (Objects.nonNull(workerEventGroup)) {
            workerEventGroup.shutdownGracefully();
        }
        log.info("closed {}", this.getClass().getSimpleName());
    }

    @Override
    public void init() {
        state.compareAndSet(State.Created, State.Initialized);
        this.initializeEventLoopGroup();
    }

    public abstract Future<Void> openAsync(ChannelInitializer<Channel> channelInitializer);

}
