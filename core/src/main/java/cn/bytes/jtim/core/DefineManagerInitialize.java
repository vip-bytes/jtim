package cn.bytes.jtim.core;

import cn.bytes.jtim.core.module.ModuleManager;
import cn.bytes.jtim.core.retry.DefineRetryManager;

/**
 * 初始订单操作管理
 */
public interface DefineManagerInitialize {

    /**
     * 打开,服务启动，或者链接打开
     *
     * @return
     */
    void open();

    void open(DefineRetryManager defineRetryManager);

    void close();

    void init();

    ModuleManager getModuleManager();

}
