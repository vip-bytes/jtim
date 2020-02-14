package cn.bytes.jtim.core.retry;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DefaultRetry implements Retry {

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
}