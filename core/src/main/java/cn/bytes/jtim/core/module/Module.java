package cn.bytes.jtim.core.module;

import cn.bytes.jtim.core.config.Configuration;
import cn.bytes.jtim.core.module.cluster.ClusterModule;
import cn.bytes.jtim.core.module.connection.ConnectionModule;
import cn.bytes.jtim.core.module.handler.ChannelHandlerModule;
import cn.bytes.jtim.core.module.initialize.InitializeModule;
import cn.bytes.jtim.core.module.retry.RetryModule;

import java.util.Map;

/**
 * <p>
 * 模块，即每个功能相依的另一个功能模块
 * </p>
 *
 * @version 1.0
 * @date 2020/2/16 21:09
 */
public interface Module extends Map<String, Module>, ModuleWare {

    <T extends Module> T getHost();

    Configuration getConfiguration();

    <T extends Module> Module then(Class<T> key, Module module);

    <T extends Module> T getModule(String key);

    <T extends Module> T getModule(Class<T> key);

    default <T extends InitializeModule> Module then(T module) {
        return this.then(InitializeModule.class, module);
    }

    default <T extends ChannelHandlerModule> Module then(T module) {
        return this.then(ChannelHandlerModule.class, module);
    }

    default <T extends RetryModule> Module then(T module) {
        return this.then(RetryModule.class, module);
    }

    default <T extends ClusterModule> Module then(T module) {
        return this.then(ClusterModule.class, module);
    }

    default <T extends ConnectionModule> Module then(T module) {
        return this.then(ConnectionModule.class, module);
    }


}
