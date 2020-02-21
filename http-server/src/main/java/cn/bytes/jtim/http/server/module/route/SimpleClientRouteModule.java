package cn.bytes.jtim.http.server.module.route;

import cn.bytes.jtim.core.config.Configuration;
import cn.bytes.jtim.core.module.connection.Connection;
import cn.bytes.jtim.core.module.connection.ConnectionModule;
import cn.bytes.jtim.core.module.handler.ChannelHandlerModule;
import cn.bytes.jtim.core.module.initialize.SimpleClientInitializeModule;
import cn.bytes.jtim.core.module.initialize.SimpleInitializeModule;
import cn.bytes.jtim.core.module.retry.SimpleRetryModule;
import cn.bytes.jtim.core.module.route.RouteModule;
import cn.bytes.jtim.core.module.route.SimpleRedisRouteModule;
import cn.bytes.jtim.core.module.route.selector.RouteKey;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RedissonClient;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

import static cn.bytes.jtim.common.constant.DefineConstant.BYTES_PUSH_SERVER_TCP;

/**
 * @version 1.0
 * @date 2020/2/21 14:05
 */
@Getter
@Slf4j
public class SimpleClientRouteModule extends SimpleRedisRouteModule implements Consumer<Collection<RouteKey>> {

    private ChannelHandlerModule channelHandlerModule;

    private ConnectionModule connectionModule;

    private Map<String, RouteKey> conned = new HashMap<>();

    public SimpleClientRouteModule(String key, RedissonClient redissonClient) {
        super(key, redissonClient);
    }

    public SimpleClientRouteModule(RedissonClient redissonClient, ChannelHandlerModule channelHandlerModule, ConnectionModule connectionModule) {
        this(BYTES_PUSH_SERVER_TCP, redissonClient);
        this.channelHandlerModule = channelHandlerModule;
        this.connectionModule = connectionModule;
        this.listener(this);
    }

    @Override
    public void accept(Collection<RouteKey> routeKeys) {
        log.info("获取连接:  {}", routeKeys);
        if (Objects.isNull(routeKeys) || routeKeys.isEmpty()) {
            return;
        }
        Map<String, RouteKey> refreshed = new HashMap<>();
        routeKeys.parallelStream().forEach(routeKey -> {
            final String id = routeKey.getId();
            final RouteKey oldRouteKey = this.conned.getOrDefault(id, null);
            if (Objects.nonNull(oldRouteKey)) {
                final Connection oldConnection = oldRouteKey.getConnection();
                if (Objects.nonNull(oldConnection) && oldConnection.isActive()) {
                    return;
                }
            }
            loopConnection(refreshed, routeKey, id, oldRouteKey);
        });

        this.conned = refreshed;
    }

    private void loopConnection(Map<String, RouteKey> refreshed, RouteKey routeKey, String id, RouteKey oldRouteKey) {

        final Connection connection = Objects.isNull(oldRouteKey) ? null : oldRouteKey.getConnection();
        if (Objects.nonNull(oldRouteKey) && Objects.nonNull(connection) && connection.isActive()) {
            refreshed.put(id, oldRouteKey);
            return;
        }
        SimpleClientInitializeModule simpleClientInitializeModule = this.doConnection(routeKey.getHost(), routeKey.getPort());
        do {
            refreshed.put(id, routeKey);
            log.info("连接服务端成功:  {}", routeKey);
        } while (!refreshed.containsKey(id) && simpleClientInitializeModule.getOpenState().equals(SimpleInitializeModule.State.Completed));
    }

    @Override
    public RouteModule route(RouteKey routeKey) {
        if (Objects.isNull(routeKey)) {
            log.warn("routeKey 不存在");
            return this;
        }

        final String host = routeKey.getHost();
        final int port = routeKey.getPort();
        final String id = String.valueOf(
                Math.abs(Objects.hash(host, port)));

        final RouteKey existRouteKey = this.conned.getOrDefault(id, null);
        if (Objects.isNull(existRouteKey)) {
            log.warn("routeKey 不存在");
            return this;
        }

        //查找当前连接地址是否存在
        Connection connection = connectionModule.getConnections().stream()
                .filter(c -> StringUtils.equalsIgnoreCase(id, c.getRemoteId())).findFirst().orElse(null);

        if (Objects.nonNull(connection) && connection.isActive()) {
            routeKey.setConnection(connection);
            setRouteKey(routeKey);
        }

        return this;
    }

    private SimpleClientInitializeModule doConnection(String host, int port) {
        Configuration configuration = new Configuration();
        configuration.setHost(host);
        configuration.setPort(port);
        SimpleClientInitializeModule simpleClientInitializeModule = new SimpleClientInitializeModule(configuration);
        simpleClientInitializeModule
                .then(SimpleRetryModule.builder().build())
                .then(channelHandlerModule)
                .then(connectionModule);
        simpleClientInitializeModule.open();
        return simpleClientInitializeModule;
    }

}
