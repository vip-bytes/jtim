package cn.bytes.jtim.core.register;

import cn.bytes.jtim.core.module.ModuleMapping;

/**
 * @version 1.0
 * @date 2020/2/15 11:34
 */
public class DefaultDefineRegisterManager implements DefineRegisterManager {
    @Override
    public ModuleMapping getModuleMapping() {
        return ModuleMapping.MODULE_REGISTER_MANAGER;
    }
}
