package cn.bytes.jtim.core.handler;

import cn.bytes.jtim.core.DefineManagerInitialize;
import cn.bytes.jtim.core.connection.DefineConnectionManager;
import cn.bytes.jtim.core.register.DefineRegisterManager;
import cn.bytes.jtim.core.router.DefineRouterManager;
import io.netty.channel.ChannelHandler;

import java.util.Objects;

public interface DefineChannelHandler extends ChannelHandler {

    default void bindManagerInitialize(DefineManagerInitialize defineManagerInitialize) {

    }

    DefineManagerInitialize getDefineManagerInitialize();

    default DefineRegisterManager getDefineRegisterManager() {
        return Objects.isNull(getDefineManagerInitialize()) ? null : getDefineManagerInitialize().getDefineRegisterManager();
    }

    default DefineHandlerManager getDefineHandlerManager() {
        return Objects.isNull(getDefineManagerInitialize()) ? null : getDefineManagerInitialize().getDefineHandlerManager();
    }

    default DefineRouterManager getDefineRouterManager() {
        return Objects.isNull(getDefineManagerInitialize()) ? null : getDefineManagerInitialize().getDefineRouterManager();
    }

    default DefineConnectionManager getDefineConnectionManager() {
        return Objects.isNull(getDefineManagerInitialize()) ? null : getDefineManagerInitialize().getDefineConnectionManager();
    }

}
