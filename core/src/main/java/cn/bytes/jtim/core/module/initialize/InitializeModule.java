package cn.bytes.jtim.core.module.initialize;

import cn.bytes.jtim.core.config.Configuration;
import cn.bytes.jtim.core.module.Module;
import cn.bytes.jtim.core.module.ModuleSlot;
import cn.bytes.jtim.core.module.retry.RetryModule;

/**
 * 初始模块
 *
 * @version 1.0
 * @date 2020/2/16 21:59
 */
public interface InitializeModule extends Module {

    /**
     * 默认使用自己模块子模块的重试模块，如果没有再使用全局的重试
     */
    default void open() {
        RetryModule retryModule = getBoarder(ModuleSlot.RETRY_SLOT);
        this.open(retryModule);
    }

    void open(RetryModule retryModule);

    void close();

    void init();

    Configuration getConfiguration();

    @Override
    default ModuleSlot mapping() {
        return ModuleSlot.INITIALIZE_SLOT;
    }
}
