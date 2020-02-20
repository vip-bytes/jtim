package cn.bytes.jtim.core.module.cluster;

import cn.bytes.jtim.core.module.AbstractSimpleModule;
import lombok.Getter;

/**
 * @version 1.0
 * @date 2020/2/17 23:05
 */
@Getter
public abstract class AbstractSimpleClusterModule extends AbstractSimpleModule implements ClusterModule {

    private ClusterServerContent content;

    @Override
    public ClusterModule content(ClusterServerContent content) {
        this.content = content;
        return this;
    }
}
