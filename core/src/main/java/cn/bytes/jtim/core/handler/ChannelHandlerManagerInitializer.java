package cn.bytes.jtim.core.handler;

import cn.bytes.jtim.core.Actuator;
import cn.bytes.jtim.core.protocol.protobuf.Message;
import io.netty.channel.ChannelInboundHandler;

/**
 * @version 1.0
 * @date 2020/2/14 14:40
 */
public interface ChannelHandlerManagerInitializer<V extends DefineChannelHandler<Message>>
        extends Actuator {

    ChannelHandlerManagerInitializer<V> addLastAndBindManager(V channelHandler);

    /**
     * 自定义的处理器转换为数组
     *
     * @return
     */
    V[] defineHandlerToArray();

}
