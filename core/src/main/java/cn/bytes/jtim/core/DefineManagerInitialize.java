package cn.bytes.jtim.core;

import cn.bytes.jtim.core.connection.DefineConnectionManager;
import cn.bytes.jtim.core.handler.DefineHandlerManager;
import cn.bytes.jtim.core.register.DefineRegisterManager;
import cn.bytes.jtim.core.retry.Retry;
import cn.bytes.jtim.core.router.DefineRouterManager;

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

    void open(Retry retry);

    void close();

    void init();

    DefineManagerInitialize use(DefineRegisterManager defineRegisterManager);

    DefineRegisterManager getDefineRegisterManager();

    DefineManagerInitialize use(DefineHandlerManager defineHandlerManager);

    DefineHandlerManager getDefineHandlerManager();

    DefineManagerInitialize use(DefineRouterManager defineRouterManager);

    DefineRouterManager getDefineRouterManager();

    DefineManagerInitialize use(DefineConnectionManager defineConnectionManager);

    DefineConnectionManager getDefineConnectionManager();

}
