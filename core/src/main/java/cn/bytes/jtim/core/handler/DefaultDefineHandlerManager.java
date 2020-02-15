package cn.bytes.jtim.core.handler;

import cn.bytes.jtim.core.protocol.protobuf.Message;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.Objects;

/**
 * @version 1.0
 * @date 2020/2/15 12:50
 */
@Slf4j
public class DefaultDefineHandlerManager extends LinkedList<DefineChannelHandler>
        implements DefineHandlerManager {

    @Override
    public DefineHandlerManager addHandlerLast(DefineChannelHandler defineChannelHandler) {
        if (Objects.isNull(defineChannelHandler)) {
            return this;
        }
        addLast(defineChannelHandler);
        log.info("添加处理器到尾部: {}", defineChannelHandler);
        return this;
    }

    @Override
    public DefineChannelHandler[] defineHandlerToArray() {
        return this.toArray(new DefineChannelHandler[]{});
    }

}
