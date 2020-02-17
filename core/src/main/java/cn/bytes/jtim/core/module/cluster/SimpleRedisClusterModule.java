package cn.bytes.jtim.core.module.cluster;

import cn.bytes.jtim.core.module.AbstractSimpleModule;
import cn.bytes.jtim.core.module.retry.RetryModule;

import java.util.function.Consumer;

/**
 * 默认的注册中心
 * <p>
 * 使用redis作为默认的注册中心
 * </p>
 *
 * @version 1.0
 * @date 2020/2/17 23:05
 */
public class SimpleRedisClusterModule extends AbstractSimpleModule implements ClusterModule {

    @Override
    public void register(RetryModule retryModule, Object registerData) {

    }

    @Override
    public void discovery(Consumer consumer) {

    }
}
