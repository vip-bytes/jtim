package cn.bytes.jtim.core.channel.module.route;

import cn.bytes.jtim.core.module.Module;
import cn.bytes.jtim.core.module.route.selector.RouteKey;
import cn.bytes.jtim.core.protocol.protobuf.Message;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * 分发模块
 * <p>
 * 1.分发模块，添加路由模块
 * 2.分发模块，根据路由模块进行消息分发
 * 3.监听分发模块的路由模块
 * 4.消息分发
 * </p>
 *
 * @version 1.0
 * @date 2020/2/17 22:55
 */
public interface RouteModule extends Module {

    /**
     * 路由绑定选择器
     *
     * @return
     */
    RouteModule route(RouteKey routeKey);

    /**
     * 获取路由选择器
     *
     * @return
     */
    Collection<RouteKey> getSelectors();

    /**
     * 监听路由选择器的变化
     *
     * @param consumer
     * @return
     */
    RouteModule listener(Consumer<Collection<RouteKey>> consumer);

    /**
     * 分发消息
     *
     * @param message
     * @return
     */
    RouteModule distribute(Message message);

    @Override
    default String key() {
        return RouteModule.class.getSimpleName();
    }


}
