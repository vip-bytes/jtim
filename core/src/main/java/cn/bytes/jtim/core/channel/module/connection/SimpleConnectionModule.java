package cn.bytes.jtim.core.channel.module.connection;

import cn.bytes.jtim.core.channel.module.AbstractSimpleModule;
import cn.bytes.jtim.core.protocol.protobuf.Message;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 连接管理模块
 *
 * @version 1.0
 * @date 2020/2/16 21:52
 */
@Slf4j
public class SimpleConnectionModule extends AbstractSimpleModule implements ConnectionModule {

    private Map<String, Connection> store = new ConcurrentHashMap<>();

    @Override
    public ConnectionModule saveConnection(Connection connection) {
        if (Objects.isNull(connection)) {
            log.debug(" saveConnection 连接信息为空");
            return this;
        }
        store.putIfAbsent(connection.getChannelId(), connection);
        log.info("保存连接:connection={} 当前连接数:{}",
                connection,
                this.onLine());
        return this;
    }

    @Override
    public ConnectionModule removeConnection(Connection connection) {
        if (Objects.isNull(connection)) {
            log.debug(" removeConnection 连接信息为空");
            return this;
        }
        store.remove(connection.getChannelId());
        return this;
    }

    @Override
    public ConnectionModule removeConnection(Channel channel) {
        if (Objects.isNull(channel)) {
            return this;
        }
        Connection delConnection = store.remove(channel.id().asLongText());
        log.info("删除连接:connection={} 当前连接数:{}",
                delConnection,
                this.onLine());
        return this;
    }

    @Override
    public int onLine() {
        return store.size();
    }

    @Override
    public ConnectionModule writeAndFlush(String channelId, Message body) {
        Connection connection = store.getOrDefault(channelId, null);
        if (Objects.isNull(connection)) {
            log.debug("连接 [{}] 不存在,不能发送信息", channelId);
            return this;
        }
        connection.writeAndFlush(body);
        return this;
    }

    @Override
    public ConnectionModule writeAndFlush(Connection connection, Message body) {
        if (Objects.isNull(connection)) {
            log.warn("当前连接为空");
            return this;
        }
        connection.writeAndFlush(body);
        return this;
    }

    @Override
    public ConnectionModule writeAndFlush(Message body) {
        this.store.values().parallelStream().forEach(connection -> {
            this.writeAndFlush(connection, body);
        });
        return this;
    }

    @Override
    public Collection<Connection> getConnections() {
        return this.store.values();
    }

}
