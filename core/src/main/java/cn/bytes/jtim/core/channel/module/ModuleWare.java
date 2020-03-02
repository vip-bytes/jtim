package cn.bytes.jtim.core.channel.module;

import cn.bytes.jtim.core.config.Configuration;

/**
 * @version 1.0
 * @date 2020/2/16 21:09
 */
public interface ModuleWare {
    /**
     * 绑定容器模块
     *
     * @param host
     * @return
     */
    void host(Module host);

    /**
     * 绑定配置信息
     *
     * @param configuration
     * @return
     */
    void configuration(Configuration configuration);
}
