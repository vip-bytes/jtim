package cn.bytes.jtim.core.module.cluster;

import cn.bytes.jtim.core.module.Module;
import cn.bytes.jtim.core.module.ModuleMapping;
import cn.bytes.jtim.core.module.retry.RetryModule;

import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @version 1.0
 * @date 2020/2/17 22:55
 */
public interface ClusterModule extends Module {

    /**
     * 服务注册
     *
     * @param clusterServerContent
     */
    default void register(ClusterServerContent clusterServerContent) {
        RetryModule retryModule = getBoarder(ModuleMapping.MODULE_RETRY);
        if (Objects.nonNull(retryModule)) {
            retryModule.reset(retryModule.retryMax(), true, retryModule.suspendTimeUnit(), retryModule.suspendStep());
        }
        this.register(retryModule, clusterServerContent);
    }

    void register(RetryModule retryModule, ClusterServerContent clusterServerContent);

    default void unRegister(ClusterServerContent clusterServerContent) {
        RetryModule retryModule = getBoarder(ModuleMapping.MODULE_RETRY);
        if (Objects.nonNull(retryModule)) {
            retryModule.reset(retryModule.retryMax(), true, retryModule.suspendTimeUnit(), retryModule.suspendStep());
        }
        this.unRegister(retryModule, clusterServerContent);
    }

    void unRegister(RetryModule retryModule, ClusterServerContent clusterServerContent);

    /**
     * 获取当前注册的服务列表
     *
     * @return
     */
    Set<ClusterServerContent> getClusterContent();

    /**
     * 监听实通知
     *
     * @param consumer
     */
    void listener(Consumer<Set<ClusterServerContent>> consumer);

    @Override
    default ModuleMapping mapping() {
        return ModuleMapping.MODULE_NETTY_WS_CLUSTER;
    }
}
