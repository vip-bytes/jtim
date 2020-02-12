package cn.bytes.jtim.core.server;

import cn.bytes.jtim.core.config.Configuration;
import cn.bytes.jtim.core.config.SocketConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.net.InetSocketAddress;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public abstract class NettyServer extends InitializerServer {

    public enum State {Created, Initialized,Starting,Started}

    protected final AtomicReference<State> state = new AtomicReference<>(State.Created);

    protected EventLoopGroup bossGroup;

    protected EventLoopGroup workerGroup;

    protected Configuration configuration;

    public NettyServer(Configuration configuration) {
        this.configuration = new Configuration(configuration);
    }

    @Override
    public void start() {
        this.init();
        if(state.compareAndSet(State.Initialized, State.Starting)){
            startAsync().syncUninterruptibly();
        }
    }

    @Override
    public void start(AfterHandler<State> afterHandler) {
        this.start();

        if(Objects.nonNull(afterHandler)) {
            afterHandler.accept(state.get());
        }

    }

    private Future<Void> startAsync() {
        selectEventLoopGroup();

        Class<? extends ServerChannel> channelClass = getChannelClass();

        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup).channel(channelClass).childHandler(this);
        //connection options
        applyConnectionOptions(b);

        InetSocketAddress addr =
                StringUtils.isNotBlank(configuration.getHost()) ?
                        new InetSocketAddress(configuration.getHost(), configuration.getPort()):
                        new InetSocketAddress(configuration.getPort());

        return b.bind(addr).addListener((FutureListener<Void>) future -> {
            if (future.isSuccess()) {
                this.state.set(State.Started);
                log.info(" {} started at port: {}",this.getClass().getSimpleName(), configuration.getPort());
            } else {
                log.error(" {} start failed at port: {}!",this.getClass().getSimpleName(), configuration.getPort());
            }
        });
    }


    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new IdleStateHandler(this.configuration.getHeartReadTime(),0,0, TimeUnit.SECONDS));
        this.handlerOptions(pipeline);
    }


    private Class<? extends ServerChannel> getChannelClass() {
        Class<? extends ServerChannel> channelClass = NioServerSocketChannel.class;
        if (configuration.isUseLinuxNativeEpoll()) {
            channelClass = EpollServerSocketChannel.class;
        }
        return channelClass;
    }

    /**
     * 服务绑定对应的处理器
     * @param pipeline
     */
    public abstract void handlerOptions(ChannelPipeline pipeline) ;

    /**
     * 初始group
     */
    protected void selectEventLoopGroup() {
        if (configuration.isUseLinuxNativeEpoll()) {
            bossGroup = new EpollEventLoopGroup(configuration.getBossThreads());
            workerGroup = new EpollEventLoopGroup(configuration.getWorkerThreads());
        } else {
            bossGroup = new NioEventLoopGroup(configuration.getBossThreads());
            workerGroup = new NioEventLoopGroup(configuration.getWorkerThreads());
        }
    }

    /**
     * 连接options
     * @param bootstrap
     */
    protected void applyConnectionOptions(ServerBootstrap bootstrap) {
        SocketConfig config = configuration.getSocketConfig();
        bootstrap.childOption(ChannelOption.TCP_NODELAY, config.isNoDelay());
        if (config.getSendBufferSize() > -1) {
            bootstrap.childOption(ChannelOption.SO_SNDBUF, config.getSendBufferSize());
        }
        if (config.getReceiveBufferSize() > -1) {
            bootstrap.childOption(ChannelOption.SO_RCVBUF, config.getReceiveBufferSize());
            bootstrap.childOption(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(config.getReceiveBufferSize()));
        }
        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, config.isKeepAlive());
        //bootstrap.childOption(ChannelOption.SO_LINGER, config.getSoLinger());
        //bootstrap.option(ChannelOption.SO_REUSEADDR, config.isReuseAddress());
        bootstrap.option(ChannelOption.SO_BACKLOG, config.getAcceptBackLog());

        //http://blog.csdn.net/u010942020/article/details/52044809
        bootstrap.childOption(ChannelOption.WRITE_BUFFER_WATER_MARK,
                new WriteBufferWaterMark(config.getWriteBufferWaterLow(), config.getWriteBufferWaterHigh()));

    }

    @Override
    public void init() {
        // todo
        state.compareAndSet(State.Created, State.Initialized);
    }


    @Override
    public void stop() {
        bossGroup.shutdownGracefully().syncUninterruptibly();
        workerGroup.shutdownGracefully().syncUninterruptibly();
        //pipelineFactory.stop();
        log.info("Socket server stopped");
    }

}
