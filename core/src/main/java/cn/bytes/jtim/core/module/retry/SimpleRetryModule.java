package cn.bytes.jtim.core.module.retry;

import cn.bytes.jtim.core.module.AbstractSimpleModule;
import cn.bytes.jtim.core.module.ModuleMapping;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
        return suspendStep;
    }

    @Override
    public void retry(Consumer<RetryStatus> consumer) {

        final int retryMax = this.retryMax();
        if (retryMax < 0) {
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

        log.info("retry [{}] open after {} {}", retryMax, suspendStep, timeUnit);
        try {
            timeUnit.sleep(suspendStep);
        } catch (InterruptedException e) {
            log.error("暂停错误", e);
        }
        this.decRetryMax();
        consumer.accept(SimpleRetryModule.RetryStatus.EXECUTE);
//        this.open(retry);
    }

    @Override
    public ModuleMapping mapping() {
        return ModuleMapping.MODULE_RETRY;
    }

}
