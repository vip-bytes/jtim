package cn.bytes.jtim.core.connection;

import cn.bytes.jtim.common.enums.ConnectionSource;
import io.netty.channel.Channel;
import lombok.Builder;
import lombok.Data;

import java.util.Objects;

/**
 * 连接包装
 *
 * @version 1.0
 * @date 2020/2/11 23:57
 */
@Data
@Builder
public class Connection {

    private String channelId;

    /**
     * 连接用户唯一标识
     */
    private String unique;

    /**
     * 认证token
     */
    private String token;

    private Channel channel;

    private String tag;

    /**
     * 连接来源
     * js,java,golang,android,ios
     */
    private ConnectionSource connectionSource;

    private long clientTime;

    public void writeAndFlush(Object body) {
        if (Objects.nonNull(channel) && channel.isActive()) {
            channel.writeAndFlush(body);
        }
    }


}
