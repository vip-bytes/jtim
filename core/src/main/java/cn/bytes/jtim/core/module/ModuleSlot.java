package cn.bytes.jtim.core.module;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @version 1.0
 * @date 2020/2/15 15:51
 */
@AllArgsConstructor
@Getter
public enum ModuleSlot {

    INITIALIZE_SLOT("INITIALIZE_SLOT", "INITIALIZE_SLOT", false),

    HANDLER_SLOT("HANDLER_SLOT", "HANDLER_SLOT", true),

    CONNECTION_SLOT("CONNECTION_SLOT", "CONNECTION_SLOT", true),

    ROUTE_SLOT("ROUTE_SLOT", "ROUTE_SLOT", false),

    RETRY_SLOT("RETRY_SLOT", "RETRY_SLOT", false),

    TCP_CLUSTER_SLOT("TCP_CLUSTER_SLOT", "TCP_CLUSTER_SLOT", false),

    WS_CLUSTER_SLOT("WS_CLUSTER_SLOT", "WS_CLUSTER_SLOT", false),
    ;

    private String name;

    private String unique;

    private boolean must;
}
