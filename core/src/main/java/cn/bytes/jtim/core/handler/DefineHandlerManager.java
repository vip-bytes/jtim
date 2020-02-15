package cn.bytes.jtim.core.handler;

import cn.bytes.jtim.core.protocol.protobuf.Message;

import java.util.Deque;

/**
 * 处理器管理
 *
 * @version 1.0
 * @date 2020/2/14 14:40
 */
public interface DefineHandlerManager extends Deque<DefineChannelHandler> {

    /**
     * 添加处理器,并且处理器绑定管理manager
     *
     * @param defineChannelHandler
     * @return
     */
    DefineHandlerManager addHandlerLast(DefineChannelHandler defineChannelHandler);

    /**
     * 自定义的处理器转换为数组
     *
     * @return
     */
    DefineChannelHandler[] defineHandlerToArray();

}
