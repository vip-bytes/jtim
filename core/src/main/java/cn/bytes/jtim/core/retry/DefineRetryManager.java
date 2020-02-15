package cn.bytes.jtim.core.retry;

import cn.bytes.jtim.core.module.Module;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * @version 1.0
 * @date 2020/2/14 11:43
 */
public interface DefineRetryManager extends Module {

    /**
     * 最大重试次数
     *
     * @return
     */
    int retryMax();

    /**
     * 减少重试次数
     */
    void decRetryMax();

    /**
     * 间隔时长
     */
    TimeUnit suspendTimeUnit();

    int suspendStep();

    void retry(Consumer<DefaultDefineRetryManager.RetryStatus> consumer);

}
