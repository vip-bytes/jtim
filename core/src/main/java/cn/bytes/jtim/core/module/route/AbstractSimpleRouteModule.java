package cn.bytes.jtim.core.module.route;

import cn.bytes.jtim.core.module.AbstractSimpleModule;
import cn.bytes.jtim.core.module.route.selector.RouteKey;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 * @date 2020/2/17 23:05
 */
@Getter
@Setter
public abstract class AbstractSimpleRouteModule extends AbstractSimpleModule implements RouteModule, Runnable {

    private RouteKey routeKey;

    private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor =
            com.sinoiov.pay.common.util.ThreadPoolUtils
                    .defaultScheduledThreadPool(2, RouteModule.class.getSimpleName());

    @Override
    public RouteModule route(RouteKey routeKey) {
        this.routeKey = routeKey;

        if (Objects.nonNull(routeKey)) {
            scheduledThreadPoolExecutor.scheduleAtFixedRate(this, 5, 5, TimeUnit.SECONDS);
        }

        return this;
    }

    @Override
    public void run() {

    }
}
