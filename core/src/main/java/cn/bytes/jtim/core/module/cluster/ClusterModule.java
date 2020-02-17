package cn.bytes.jtim.core.module.cluster;

import cn.bytes.jtim.core.module.Module;
import cn.bytes.jtim.core.module.ModuleMapping;
import cn.bytes.jtim.core.module.retry.RetryModule;

import java.util.function.Consumer;

/**
 * @version 1.0
 * @date 2020/2/17 22:55
 */
public interface ClusterModule<R, D> extends Module {

    /**
     * 使用自身子模块的重试模块,如果子模块没有重试机制，则不重试
     */
    default void register(R registerData) {
        RetryModule retryModule = getBoarder(ModuleMapping.MODULE_RETRY);
        this.register(retryModule, registerData);
    }

    /**
     * 服务注册
     *
     * @param retryModule
     */
    void register(RetryModule retryModule, R registerData);

    /**
     * 服务发现
     *
     * @param consumer<DD>
     */
    void discovery(Consumer<D> consumer);

    @Override
    default ModuleMapping mapping() {
        return ModuleMapping.MODULE_CLUSTER;
    }
}
