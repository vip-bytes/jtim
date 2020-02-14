package cn.bytes.jtim.core;

import cn.bytes.jtim.core.retry.Retry;

/**
 * 服务
 * TODO 每个服务需要自定义自己的消息处理器
 */
public interface Actuator {

    /**
     * 打开,服务启动，或者链接打开
     *
     * @return
     */
    void open();

    void open(Retry retry);

    void close();

    void init();

    //TODO 服务注册

    //TODO 消息分发处理

}
