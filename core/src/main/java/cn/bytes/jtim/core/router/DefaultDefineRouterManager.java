package cn.bytes.jtim.core.router;

import cn.bytes.jtim.core.module.ModuleMapping;

/**
 * @version 1.0
 * @date 2020/2/15 11:37
 */
public class DefaultDefineRouterManager implements DefineRouterManager {

    @Override
    public ModuleMapping getModuleMapping() {
        return ModuleMapping.MODULE_ROUTER_MANAGER;
    }

}
