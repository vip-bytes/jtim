package cn.bytes.jtim.core.handler;

import cn.bytes.jtim.core.DefineManagerInitialize;
import cn.bytes.jtim.core.connection.DefineConnectionManager;
import cn.bytes.jtim.core.module.Module;
import cn.bytes.jtim.core.module.ModuleMapping;
import cn.bytes.jtim.core.register.DefineRegisterManager;
import cn.bytes.jtim.core.router.DefineRouterManager;
import io.netty.channel.ChannelHandler;

import java.util.Objects;

import static cn.bytes.jtim.core.module.ModuleMapping.*;

public interface DefineChannelHandler extends ChannelHandler {

    default void bindManagerInitialize(DefineManagerInitialize defineManagerInitialize) {

    }

    DefineManagerInitialize getDefineManagerInitialize();

    default DefineRegisterManager getDefineRegisterManager() {
        return getModule(MODULE_REGISTER_MANAGER);
    }

    default DefineHandlerManager getDefineHandlerManager() {
        return getModule(MODULE_HANDLER_MANAGER);
    }

    default DefineRouterManager getDefineRouterManager() {
        return getModule(MODULE_ROUTER_MANAGER);
    }

    default DefineConnectionManager getDefineConnectionManager() {
        return getModule(MODULE_CONNECTION_MANAGER);
    }

    default <T extends Module> T getModule(ModuleMapping moduleMapping) {
        return Objects.isNull(getDefineManagerInitialize()) ? null :
                Objects.isNull(getDefineManagerInitialize().getModuleManager()) ? null :
                        getDefineManagerInitialize().getModuleManager().getModule(moduleMapping);
    }

}
