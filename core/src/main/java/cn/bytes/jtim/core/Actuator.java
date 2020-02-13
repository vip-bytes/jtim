package cn.bytes.jtim.core;

import cn.bytes.jtim.core.ActuatorInitializer;
import cn.bytes.jtim.core.AfterHandler;

/**
 * 服务
 * TODO 每个服务需要自定义自己的消息处理器
 */
public interface Actuator {

    /**
     * 打开,服务启动，或者链接打开
     */
    void open();

    void open(AfterHandler<ActuatorInitializer.State> afterHandler);

    void close();

    void init();

}
