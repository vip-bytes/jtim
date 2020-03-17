package cn.bytes.jtim.core.channel.module.initialize;

import cn.bytes.jtim.core.channel.config.Configuration;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.util.concurrent.Future;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * 初始服务模块
 *
 * @version 1.0
 * @date 2020/2/16 22:00
 */
@Getter
@Slf4j
public class SimpleServerInitializeModule extends SimpleInitializeModule implements InitializeModule {

    public SimpleServerInitializeModule(Configuration configuration) {
        super(configuration);
    }

    @Override
    public Future<Void> openAsync(ChannelInitializer<Channel> channelInitializer) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(getBossEventGroup(), getWorkerEventGroup())
                .channel(getNioServerSocketChannelClass())
                .childHandler(channelInitializer);
        this.options(serverBootstrap);
        return serverBootstrap.bind(getSocketAddress());
    }

}
