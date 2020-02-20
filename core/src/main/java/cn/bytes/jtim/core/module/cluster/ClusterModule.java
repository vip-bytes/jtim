package cn.bytes.jtim.core.module.cluster;

import cn.bytes.jtim.core.module.Module;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * @version 1.0
 * @date 2020/2/17 22:55
 */
public interface ClusterModule extends Module {

    ClusterModule content(ClusterServerContent content);

    ClusterModule register();

    ClusterModule unRegister();

    /**
     * 获取当前注册的服务列表
     *
     * @return
     */
    Collection<ClusterServerContent> getClusterContent();

    /**
     * 监听实通知
     *
     * @param consumer
     */
    ClusterModule listener(Consumer<Collection<ClusterServerContent>> consumer);

    @Override
    default String key() {
        return ClusterModule.class.getSimpleName();
    }


}
