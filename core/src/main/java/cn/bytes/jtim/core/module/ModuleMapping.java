package cn.bytes.jtim.core.module;

import cn.bytes.jtim.core.register.DefineRegisterManager;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @version 1.0
 * @date 2020/2/15 15:51
 */
@AllArgsConstructor
@Getter
public enum ModuleMapping {

    MODULE_HANDLER_MANAGER("MODULE_HANDLER_MANAGER", "002", true),

    MODULE_CONNECTION_MANAGER("MODULE_CONNECTION_MANAGER", "002", true),

    MODULE_ROUTER_MANAGER("MODULE_ROUTER_MANAGER", "003", false),

    MODULE_REGISTER_MANAGER("MODULE_REGISTER_MANAGER", "004", false);

    private String name;

    private String unique;

    private boolean must;
}
