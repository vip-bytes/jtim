package cn.bytes.jtim.core.connection;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Objects;

/**
 * @author maliang@sioniov.com
 * @version 1.0
 * @date 2020/2/12 0:18
 */
@Slf4j
public class DefaultConnectionManager extends HashMap<String,Connection> implements ConnectionManager<String,Connection> {

    @Override
    public void saveConnection(Connection connection) {
        if(Objects.isNull(connection)) {
            log.debug(" saveConnection 连接信息为空");
            return;
        }
        this.putIfAbsent(connection.getChannelId(),connection);
    }

    @Override
    public void writeAndFlush(String channelId, Object body) {
        Connection connection = getOrDefault(channelId,null);
        if(Objects.isNull(connection)) {
            log.debug("连接 [{}] 不存在,不能发送信息",channelId);
            return;
        }
        connection.writeAndFlush(body);
    }

}
