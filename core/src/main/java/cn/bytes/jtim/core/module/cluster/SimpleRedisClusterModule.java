package cn.bytes.jtim.core.module.cluster;

import cn.bytes.jtim.core.module.initialize.InitializeModule;
import cn.bytes.jtim.core.module.initialize.SimpleInitializeModule;
import com.sinoiov.pay.common.util.ThreadPoolUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.redisson.api.map.event.EntryEvent;
import org.redisson.api.map.event.EntryExpiredListener;

import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * @version 1.0
 * @date 2020/2/18 14:10
 */
@Slf4j
public class SimpleRedisClusterModule extends AbstractSimpleClusterModule implements Runnable, EntryExpiredListener<String, ClusterModule> {

    private static final String TOPIC = "cluster:data:notify";

    private static final String NOTIFY_DATA = "1";

    private RedissonClient redissonClient;

    private String key;

    private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor =
            ThreadPoolUtils.defaultScheduledThreadPool(2, ClusterModule.class.getSimpleName());

    RMapCache<String, ClusterServerContent> mapCache;

    public SimpleRedisClusterModule(String key, RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
        this.key = key;
        scheduledThreadPoolExecutor.scheduleAtFixedRate(this, 5, 5, TimeUnit.SECONDS);
        mapCache = redissonClient.getMapCache(key);
        mapCache.addListener(this);
    }

    @Override
    public ClusterModule register() {

        if (Objects.isNull(getContent())) {
            return this;
        }
        try {
            String id = super.getContent().getId();

            if (mapCache.containsKey(id)) {
                mapCache.put(id, getContent(), 10, TimeUnit.SECONDS);
                return this;
            }

            mapCache.fastPut(id, getContent(), 10, TimeUnit.SECONDS);
            topicNotify();
        } catch (Exception e) {
            log.error("", e);
        }
        return this;
    }

    private void topicNotify() {
        long notify = redissonClient.getTopic(TOPIC).publish(NOTIFY_DATA);
        log.info("注册返回结果  notify={}", notify);
    }

    @Override
    public ClusterModule unRegister() {
        try {
            String id = super.getContent().getId();
            RMapCache<String, ClusterServerContent> mapCache = redissonClient.getMapCache(this.key);
            mapCache.remove(id);
            topicNotify();
        } catch (Exception e) {
            log.error("", e);
        }
        return this;
    }

    @Override
    public Collection<ClusterServerContent> getClusterContent() {
        RMapCache<String, ClusterServerContent> mapCache = redissonClient.getMapCache(this.key);
        return mapCache.values();
    }

    @Override
    public ClusterModule listener(Consumer<Collection<ClusterServerContent>> consumer) {
        redissonClient.getTopic(TOPIC).addListener(String.class, (charSequence, data) -> {
            if (StringUtils.equalsIgnoreCase(charSequence, TOPIC) && data.equalsIgnoreCase(NOTIFY_DATA)) {
                consumer.accept(getClusterContent());
            }
        });
        return this;
    }

    @Override
    public void run() {
        try {
            InitializeModule initializeModule = getHost();
            if (Objects.nonNull(getContent()) && SimpleInitializeModule.State.Completed.equals(initializeModule.getOpenState())) {
                this.register();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onExpired(EntryEvent entryEvent) {
        this.topicNotify();
    }
}
