package cn.bytes.jtim.core.module.route;

import cn.bytes.jtim.core.module.connection.Connection;
import cn.bytes.jtim.core.module.route.selector.RouteKey;
import cn.bytes.jtim.core.protocol.protobuf.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RMapCache;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.map.event.EntryEvent;
import org.redisson.api.map.event.EntryExpiredListener;

import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

/**
 * @version 1.0
 * @date 2020/2/18 14:10
 */
@Slf4j
public class SimpleRedisRouteModule extends AbstractSimpleRouteModule implements Runnable {

    private static final String TOPIC = "distribute:notify";

    private static final String NOTIFY_DATA = "1";

    private RedissonClient redissonClient;

    private String key;

    private RMapCache<String, RouteKey> mapCache;

    private RTopic topic;

    private final Lock lock = new ReentrantLock(true);

    public SimpleRedisRouteModule(String key, RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
        this.key = key;
        init(key, redissonClient);
    }

    private void init(String key, RedissonClient redissonClient) {
        mapCache = redissonClient.getMapCache(key);
        topic = redissonClient.getTopic(TOPIC);
    }

    @Override
    public Collection<RouteKey> getSelectors() {
        RMapCache<String, RouteKey> mapCache = redissonClient.getMapCache(this.key);
        return mapCache.values();
    }

    @Override
    public RouteModule listener(Consumer<Collection<RouteKey>> consumer) {
        consumer.accept(getSelectors());
        MessageListener messageListener = new MessageListener(consumer, this);
        topic.addListener(String.class, messageListener);
        mapCache.addListener(messageListener);
        return this;
    }

    @Override
    public RouteModule distribute(Message message) {

        RouteKey routeKey = getRouteKey();

        if (Objects.isNull(routeKey)) {
            return this;
        }

        Connection connection = routeKey.getConnection();
        if (Objects.isNull(connection) || !connection.isActive()) {
            log.warn("连接信息异常 : {}", connection);
            return this;
        }
        connection.writeAndFlush(message);
        return this;
    }

    private void bind() {
        lock.lock();
        if (Objects.isNull(getRouteKey())) {
            return;
        }
        try {
            String id = super.getRouteKey().getId();
            if (mapCache.containsKey(id)) {
                mapCache.put(id, getRouteKey(), 10, TimeUnit.SECONDS);
                return;
            }
            mapCache.fastPut(id, getRouteKey(), 10, TimeUnit.SECONDS);
            topicNotify();
        } catch (Exception e) {
            log.error("", e);
        } finally {
            lock.unlock();
        }
    }

    private void topicNotify() {
        long notify = topic.publish(NOTIFY_DATA);
        log.info("注册返回结果  notify={}", notify);
    }

    @Override
    public void run() {
        try {
            if (Objects.nonNull(getRouteKey()) && getRouteKey().isBind()) {
                this.bind();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Data
    @AllArgsConstructor
    private static class MessageListener implements
            org.redisson.api.listener.MessageListener<String>, EntryExpiredListener<String, RouteKey> {

        private Consumer<Collection<RouteKey>> consumer;

        private SimpleRedisRouteModule redisClusterModule;

        @Override
        public void onMessage(CharSequence charSequence, String data) {
            if (StringUtils.equalsIgnoreCase(charSequence, TOPIC) && data.equalsIgnoreCase(NOTIFY_DATA)) {
                consumer.accept(redisClusterModule.getSelectors());
            }
        }

        @Override
        public void onExpired(EntryEvent<String, RouteKey> entryEvent) {
            consumer.accept(redisClusterModule.getSelectors());
        }
    }


}
