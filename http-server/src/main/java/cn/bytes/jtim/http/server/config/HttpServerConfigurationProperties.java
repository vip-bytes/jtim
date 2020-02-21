package cn.bytes.jtim.http.server.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @version 1.0
 * @date 2020/2/21 13:48
 */
@ConfigurationProperties(prefix = "bytes.http.server")
@Data
public class HttpServerConfigurationProperties {

}
