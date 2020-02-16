package cn.bytes.jtim.core.module.initialize;

import cn.bytes.jtim.core.config.Configuration;
import cn.bytes.jtim.core.module.Module;
import cn.bytes.jtim.core.module.ModuleMapping;
import cn.bytes.jtim.core.module.retry.RetryModule;

/**
 * 初始模块
 *
 * @version 1.0
 * @date 2020/2/16 21:59
 */
public interface InitializeModule extends Module {

    void open();

    void open(RetryModule retryModule);

    void close();

    void init();

    Configuration getConfiguration();

    @Override
    default ModuleMapping mapping() {
        return ModuleMapping.MODULE_INITIALIZE;
    }
}
