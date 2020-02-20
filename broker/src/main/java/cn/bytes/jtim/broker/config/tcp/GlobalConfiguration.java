package cn.bytes.jtim.broker.config.tcp;

import cn.bytes.jtim.broker.module.handler.SimpleChannelHandlerTcpModule;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

import static cn.bytes.jtim.common.constant.DefineConstant.PROPER_TCP_ENABLE;

/**
 * @version 1.0
 * @date 2020/2/20 20:23
 */
@ConditionalOnProperty(name = PROPER_TCP_ENABLE, havingValue = "true")
public class GlobalConfiguration {

    @Bean
    public SimpleChannelHandlerTcpModule simpleChannelHandlerTcpModule() {
        return new SimpleChannelHandlerTcpModule();
    }
}
