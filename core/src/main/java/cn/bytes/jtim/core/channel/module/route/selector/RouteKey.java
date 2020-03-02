package cn.bytes.jtim.core.channel.module.route.selector;

import cn.bytes.jtim.core.module.connection.Connection;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * @version 1.0
 * @date 2020/2/21 15:22
 */
@Getter
@Setter
@Slf4j
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RouteKey implements Serializable {

    @Builder.Default
    private boolean bind = true;

    private String id;

    private String host;

    private int port;

    private Connection connection;


}
