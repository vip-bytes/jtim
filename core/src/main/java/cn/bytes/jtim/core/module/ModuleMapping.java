package cn.bytes.jtim.core.module;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @version 1.0
 * @date 2020/2/15 15:51
 */
@AllArgsConstructor
@Getter
public enum ModuleMapping {

    MODULE_INIT_MANAGER("MODULE_INIT_MANAGER", "MODULE_INIT_MANAGER", false),

    MODULE_HANDLER_MANAGER("MODULE_HANDLER_MANAGER", "MODULE_HANDLER_MANAGER", true),

    MODULE_CONNECTION_MANAGER("MODULE_CONNECTION_MANAGER", "MODULE_CONNECTION_MANAGER", true),

    MODULE_ROUTER_MANAGER("MODULE_ROUTER_MANAGER", "MODULE_ROUTER_MANAGER", false),

    MODULE_REGISTER_MANAGER("MODULE_REGISTER_MANAGER", "MODULE_REGISTER_MANAGER", false),

    MODULE_RETRY_MANAGER("MODULE_RETRY_MANAGER", "MODULE_RETRY_MANAGER", false),
    ;

    private String name;

    private String unique;

    private boolean must;
}
