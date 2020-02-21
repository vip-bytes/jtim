package cn.bytes.jtim.http.server.context;

import cn.bytes.jtim.http.server.config.HttpServerConfigurationProperties;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @version 1.0
 * @date 2020/2/21 12:32
 */
@EnableConfigurationProperties(HttpServerConfigurationProperties.class)
@Slf4j
@Getter
public class ServerApplicationContext {

    private HttpServerConfigurationProperties httpServerConfigurationProperties;

    public ServerApplicationContext(HttpServerConfigurationProperties httpServerConfigurationProperties) {
        this.httpServerConfigurationProperties = httpServerConfigurationProperties;
    }

}
