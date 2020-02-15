package cn.bytes.jtim.core;

import cn.bytes.jtim.core.config.Configuration;
import cn.bytes.jtim.core.config.SocketConfig;
import cn.bytes.jtim.core.connection.DefaultDefineConnectionManager;
import cn.bytes.jtim.core.connection.DefineConnectionManager;
import cn.bytes.jtim.core.handler.DefaultDefineHandlerManager;
import cn.bytes.jtim.core.handler.DefaultDefineInitialize;
import cn.bytes.jtim.core.handler.DefineHandlerManager;
import cn.bytes.jtim.core.retry.Retry;
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

@Slf4j
@Getter
public abstract class NettyDefineInitialize extends DefaultDefineInitialize {

    public NettyDefineInitialize(Configuration configuration,
                                 DefineHandlerManager defineHandlerManager,
                                 DefineConnectionManager defineConnectionManager) {
        super(configuration, defineHandlerManager, defineConnectionManager);
    }

    public enum State {Created, Initialized, Executing, Completed}

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
        this.open((Retry) null);
    }

    @Override
    public void open(Retry retry) {

        this.init();

        this.openAsync().addListener((FutureListener<Void>) future -> {
            if (future.isSuccess()) {
                this.state.set(State.Completed);
                log.info(" {} at addr: {}:{}", this.getClass().getSimpleName(), getConfiguration().getHost(), getConfiguration().getPort());
            } else {
                log.error(" {} failed at addr: {}:{}", this.getClass().getSimpleName(), getConfiguration().getHost(), getConfiguration().getPort());

                this.close();
                if (Objects.nonNull(retry)) {
                    final int retryMax = retry.retryMax();
                    if (retryMax < 0) {
                        log.warn("重试结束");
                        this.close();
                        return;
                    }

                    final TimeUnit timeUnit = retry.suspendTimeUnit();
                    final int suspendStep = retry.suspendStep();
                    if (Objects.isNull(timeUnit) || suspendStep <= 0) {
                        log.warn("重试设置信息错误: timeUnit = {}, step = {}", timeUnit, suspendStep);
                        this.close();
                        return;
                    }

                    log.info("retry [{}] open after {} {}", retryMax, suspendStep, timeUnit);
                    try {
                        timeUnit.sleep(suspendStep);
                    } catch (InterruptedException e) {
                        log.error("", e);
                    }

                    retry.decRetryMax();
                    this.open(retry);
                }

            }
        });

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

//    public ConnectionManager<String, Connection> getConnectionManager() {
//        return connectionManager;
//    }

}
