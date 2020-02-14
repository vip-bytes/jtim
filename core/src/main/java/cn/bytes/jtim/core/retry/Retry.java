package cn.bytes.jtim.core.retry;

import java.util.concurrent.TimeUnit;

/**
 * 重试
 *
 * @version 1.0
 * @date 2020/2/14 11:43
 */
public interface Retry {

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
}
