package cn.bytes.jtim.core.server;

/**
 * 服务
 * TODO 每个服务需要自定义自己的消息处理器
 */
public interface Server{

    void start();

    /**
     * 启动完成
     * @param afterHandler
     */
    void start(AfterHandler<NettyServer.State> afterHandler);

    void stop();

    void init();

}
