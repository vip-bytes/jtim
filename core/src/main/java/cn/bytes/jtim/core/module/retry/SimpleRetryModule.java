package cn.bytes.jtim.core.module.retry;

import cn.bytes.jtim.core.module.AbstractSimpleModule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * @version 1.0
 * @date 2020/2/16 22:21
 */
@Slf4j
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SimpleRetryModule extends AbstractSimpleModule implements RetryModule {

    public enum RetryStatus {
        CLOSE, EXECUTE;
    }

    @Builder.Default
    private AtomicInteger retryMax = new AtomicInteger(5);

    @Builder.Default
    private TimeUnit timeUnit = TimeUnit.SECONDS;

    @Builder.Default
    private int suspendStep = 3;

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
    public boolean isLoop() {
        return false;
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
        return suspendStep;
    }

    @Override
    public void retry(Consumer<RetryStatus> consumer) {

        final int retryMax = this.retryMax();
        if (retryMax < 0 && !isLoop()) {
            log.warn("重试结束");
            consumer.accept(SimpleRetryModule.RetryStatus.CLOSE);
            return;
        }

        final TimeUnit timeUnit = this.suspendTimeUnit();
        final int suspendStep = this.suspendStep();
        if (Objects.isNull(timeUnit) || suspendStep <= 0) {
            log.warn("重试设置信息错误: timeUnit = {}, step = {}", timeUnit, suspendStep);
            consumer.accept(SimpleRetryModule.RetryStatus.CLOSE);
            return;
        }
        log.info("retry [retryMax={},loop={}]  after {} {}", retryMax, isLoop(), suspendStep, timeUnit);
        try {
            timeUnit.sleep(suspendStep);
        } catch (InterruptedException e) {
            log.error("暂停错误", e);
        }

        if (!isLoop()) {
            this.decRetryMax();
        }
        consumer.accept(SimpleRetryModule.RetryStatus.EXECUTE);
    }

    @Override
    public void reset(int retryMax, boolean loop, TimeUnit suspendTimeUnit, int suspendStep) {
        this.setRetryMax(new AtomicInteger(retryMax));
        this.setLoop(loop);
        this.setSuspendStep(suspendStep);
        this.setTimeUnit(suspendTimeUnit);
    }

}
