package cn.bytes.jtim.core.channel.module.task;

import cn.bytes.jtim.core.module.Module;

import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 * @date 2020/2/20 22:16
 */
public interface RunTaskModule extends Module {

    void delay(long delay, TimeUnit timeUnit);

    @Override
    default String key() {
        return RunTaskModule.class.getSimpleName();
    }
}
