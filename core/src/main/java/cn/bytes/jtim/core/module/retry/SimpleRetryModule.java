package cn.bytes.jtim.core.module.retry;

import cn.bytes.jtim.core.module.AbstractSimpleModule;
import com.sinoiov.pay.common.util.ThreadPoolUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * @version 1.0
 * @date 2020/2/16 22:21
 */
@Slf4j
@Builder
@Getter
@Setter
public class SimpleRetryModule extends AbstractSimpleModule implements RetryModule, Runnable {

    @Builder.Default
    private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor =
            ThreadPoolUtils.defaultScheduledThreadPool(2, RetryModule.class.getSimpleName());

    private Consumer<RetryStatus> consumer;

    public enum RetryStatus {
        CLOSE, EXECUTE;
    }

    @Builder.Default
    private AtomicInteger retryMax = new AtomicInteger(5);

    @Builder.Default
    private TimeUnit timeUnit = TimeUnit.SECONDS;

    @Builder.Default
    private int delay = 3;

    /**
     * 如果这个参数为true 那么retryMax的次数将没有意义
     */
    @Builder.Default
    private boolean loop = false;

    @Override
    public int retryMax() {
        return retryMax.get();
    }

    @Override
    public void decRetryMax() {
        retryMax.decrementAndGet();
    }

    @Override
    public TimeUnit suspendTimeUnit() {
        return timeUnit;
    }

    @Override
    public int suspendStep() {
        return delay;
    }

    @Override
    public void retry(Consumer<RetryStatus> consumer) {
        this.setConsumer(consumer);
        scheduledThreadPoolExecutor.schedule(this, delay, timeUnit);
    }

    @Override
    public void run() {
        final int retryMax = this.retryMax();
        final boolean loop = this.isLoop();
        if (retryMax > 0 || loop) {
            log.info("retry delay={} retryMax={},loop={} ", this.getDelay(), retryMax, loop);
            if (!loop) {
                this.decRetryMax();
            }
            consumer.accept(RetryStatus.EXECUTE);
        } else {
            this.getScheduledThreadPoolExecutor().shutdown();
            consumer.accept(RetryStatus.CLOSE);
        }
    }

    @Override
    public void reset(int retryMax, boolean loop, TimeUnit timeUnit, int delay) {
        this.setRetryMax(new AtomicInteger(retryMax));
        this.setLoop(loop);
        this.setDelay(delay);
        this.setTimeUnit(timeUnit);
    }

}
