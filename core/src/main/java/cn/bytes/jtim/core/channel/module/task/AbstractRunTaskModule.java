package cn.bytes.jtim.core.channel.module.task;

import cn.bytes.jtim.core.channel.module.AbstractSimpleModule;
import cn.bytes.jtim.core.utils.ThreadPoolUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 * @date 2020/2/20 22:25
 */
@Getter
@Slf4j
public class AbstractRunTaskModule extends AbstractSimpleModule implements RunTaskModule {

    private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = ThreadPoolUtils.defaultScheduledThreadPool(2, AbstractRunTaskModule.class.getSimpleName());

    private long delay = 3;

    private TimeUnit timeUnit = TimeUnit.SECONDS;

    @Override
    public void delay(long delay, TimeUnit timeUnit) {
        this.delay = delay;
        this.timeUnit = timeUnit;
    }
}
