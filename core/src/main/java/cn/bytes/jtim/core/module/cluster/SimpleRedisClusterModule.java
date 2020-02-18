package cn.bytes.jtim.core.module.cluster;

import cn.bytes.jtim.core.module.retry.RetryModule;
import cn.bytes.jtim.core.module.retry.SimpleRetryModule;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;

import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @version 1.0
 * @date 2020/2/18 14:10
 */
@Slf4j
public class SimpleRedisClusterModule extends AbstractSimpleClusterModule {

    private static final String TOPIC = "cluster:data:notify";

    private static final String NOTIFY_DATA = "1";

    private RedissonClient redissonClient;

    private String key;

    public SimpleRedisClusterModule(String key, RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
        this.key = key;
    }

    @Override
    public void register(RetryModule retryModule, ClusterServerContent clusterServerContent) {
        try {
            RSet<ClusterServerContent> rSet = redissonClient.getSet(this.key);
            boolean result = rSet.add(clusterServerContent);
            long notify = redissonClient.getTopic(TOPIC).publish(NOTIFY_DATA);
            log.info("注册返回结果 {} notify={}", result, notify);
        } catch (Exception e) {
            if (Objects.nonNull(retryModule)) {
                retryModule.retry(retryStatus -> {
                    if (Objects.equals(retryStatus, SimpleRetryModule.RetryStatus.EXECUTE)) {
                        this.register(retryModule, clusterServerContent);
                    }
                });
            }
            log.error("", e);
        }
    }

    @Override
    public void unRegister(RetryModule retryModule, ClusterServerContent clusterServerContent) {
        try {
            RSet<ClusterServerContent> rSet = redissonClient.getSet(this.key);
            boolean result = rSet.remove(clusterServerContent);
            long notify = redissonClient.getTopic(TOPIC).publish(NOTIFY_DATA);
            log.info("取消注册返回结果 {} notify={}", result, notify);
        } catch (Exception e) {
            if (Objects.nonNull(retryModule)) {
                retryModule.retry(retryStatus -> {
                    if (Objects.equals(retryStatus, SimpleRetryModule.RetryStatus.EXECUTE)) {
                        this.unRegister(retryModule, clusterServerContent);
                    }
                });
            }
            log.error("", e);
        }
    }

    @Override
    public Set<ClusterServerContent> getClusterContent() {
        return redissonClient.getSet(this.key);
    }

    @Override
    public void listener(Consumer<Set<ClusterServerContent>> consumer) {
        redissonClient.getTopic(TOPIC).addListener(String.class, (charSequence, data) -> {
            if (StringUtils.equalsIgnoreCase(charSequence, TOPIC) && data.equalsIgnoreCase(NOTIFY_DATA)) {
                consumer.accept(getClusterContent());
            }
        });
    }
}
