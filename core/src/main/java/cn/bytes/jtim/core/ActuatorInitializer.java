package cn.bytes.jtim.core;

import cn.bytes.jtim.core.config.Configuration;
import cn.bytes.jtim.core.config.SocketConfig;
import cn.bytes.jtim.core.connection.Connection;
import cn.bytes.jtim.core.connection.ConnectionManager;
import cn.bytes.jtim.core.connection.DefaultConnectionManager;
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
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.net.InetSocketAddress;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Getter
public abstract class ActuatorInitializer extends ChannelInitializer<Channel> implements Actuator {

    /**
     * 连接管理器
     */
    private static final ConnectionManager<String, Connection> connectionManager = new DefaultConnectionManager();

    public enum State {Created, Initialized, Executing, Completed}

    public final AtomicReference<State> state = new AtomicReference<>(State.Created);

    public EventLoopGroup bossGroup;

    public EventLoopGroup workerGroup;

    public Configuration configuration;

    public ActuatorInitializer(Configuration configuration) {
        this.configuration = new Configuration(configuration);
    }

    @Override
    public void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new IdleStateHandler(this.configuration.getHeartReadTime(), 0, 0, TimeUnit.SECONDS));
        this.channelPipelineOptions(pipeline);
    }

    /**
     * 服务绑定对应的处理器
     *
     * @param pipeline
     */
    public abstract void channelPipelineOptions(ChannelPipeline pipeline);

    public abstract Future<Void> openAsync();

    /**
     * 初始group
     */
    public void selectEventLoopGroup() {
        if (configuration.isUseLinuxNativeEpoll()) {
            bossGroup = new EpollEventLoopGroup(configuration.getBossThreads());
            workerGroup = new EpollEventLoopGroup(configuration.getWorkerThreads());
        } else {
            bossGroup = new NioEventLoopGroup(configuration.getBossThreads());
            workerGroup = new NioEventLoopGroup(configuration.getWorkerThreads());
        }
    }

    public InetSocketAddress getSocketAddress() {
        return StringUtils.isNotBlank(configuration.getHost()) ?
                new InetSocketAddress(configuration.getHost(), configuration.getPort()) :
                new InetSocketAddress(configuration.getPort());
    }

    public Class<? extends ServerChannel> getNioServerSocketChannelClass() {
        Class<? extends ServerChannel> channelClass = NioServerSocketChannel.class;
        if (configuration.isUseLinuxNativeEpoll()) {
            channelClass = EpollServerSocketChannel.class;
        }
        return channelClass;
    }

    public Class<? extends Channel> getNioSocketChannelClass() {
        Class<? extends Channel> channelClass = NioSocketChannel.class;
        if (configuration.isUseLinuxNativeEpoll()) {
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
    public void open() {
        this.init();
        if (state.compareAndSet(State.Initialized, State.Executing)) {
            openAsync().syncUninterruptibly();
        }
    }

    @Override
    public void open(AfterHandler<State> afterHandler) {
        this.open();
        if (Objects.nonNull(afterHandler)) {
            afterHandler.accept(state.get());
        }

    }

    @Override
    public void close() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}
