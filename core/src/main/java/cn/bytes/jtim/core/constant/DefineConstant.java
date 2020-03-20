package cn.bytes.jtim.core.constant;

import cn.bytes.jtim.core.channel.module.connection.Connection;
import io.netty.util.AttributeKey;

/**
 * @version 1.0
 * @date 2020/3/20 15:43
 */
public class DefineConstant {

    public static final String TOKEN = "token";

    public static final String SOURCE = "source";

    public static final String WEBSOCKET_PATH = "/ws";

    public static final AttributeKey<Connection> ATTRIBUTE_CONNECTION = AttributeKey.valueOf("connection");
}
