package cn.bytes.jtim.core.connection;

import cn.bytes.jtim.core.module.ModuleMapping;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Objects;

/**
 * @version 1.0
 * @date 2020/2/12 0:18
 */
@Slf4j
public class DefaultDefineConnectionManager extends HashMap<String, Connection> implements DefineConnectionManager {

    @Override
    public DefineConnectionManager saveConnection(Connection connection) {
        if (Objects.isNull(connection)) {
            log.debug(" saveConnection 连接信息为空");
            return this;
        }
        this.putIfAbsent(connection.getChannelId(), connection);

        this.onLine();
        return this;
    }

    @Override
    public DefineConnectionManager removeConnection(Connection connection) {
        if (Objects.isNull(connection)) {
            log.debug(" removeConnection 连接信息为空");
            return this;
        }
        this.remove(connection.getChannelId());

        return this;
    }

    @Override
    public DefineConnectionManager removeConnection(Channel channel) {

        if (Objects.isNull(channel)) {
            return this;
        }
        this.remove(channel.id().asLongText());
        this.onLine();
        return this;
    }

    @Override
    public int onLine() {
        int number = this.values().size();
        log.info("当前连接数量: {}", number);
        return number;
    }

    @Override
    public DefineConnectionManager writeAndFlush(String channelId, Object body) {
        Connection connection = getOrDefault(channelId, null);
        if (Objects.isNull(connection)) {
            log.debug("连接 [{}] 不存在,不能发送信息", channelId);
            return this;
        }
        connection.writeAndFlush(body);
        return this;
    }

    @Override
    public DefineConnectionManager writeAndFlush(Connection connection, Object body) {
        if (Objects.isNull(connection)) {
            log.warn("当前连接为空");
            return this;
        }
        connection.writeAndFlush(body);
        return this;
    }

    @Override
    public ModuleMapping getModuleMapping() {
        return ModuleMapping.MODULE_CONNECTION_MANAGER;
    }

}
