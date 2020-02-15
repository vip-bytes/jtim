package cn.bytes.jtim.core.register;

import cn.bytes.jtim.core.module.Module;
import cn.bytes.jtim.core.module.ModuleMapping;
import cn.bytes.jtim.core.retry.DefineRetryManager;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

/**
 * @version 1.0
 * @date 2020/2/15 11:34
 */
@Slf4j
public class DefaultDefineRegisterManager extends HashMap<ModuleMapping, Module> implements DefineRegisterManager {

    @Override
    public ModuleMapping getModuleMapping() {
        return ModuleMapping.MODULE_REGISTER_MANAGER;
    }

    @Override
    public boolean register(String host, int port, DefineRetryManager defineRetryManager) {
        return false;
    }

    @Override
    public Module module(Module module) {
        ModuleMapping moduleMapping = module.getModuleMapping();
        if (!ModuleMapping.MODULE_RETRY_MANAGER.equals(moduleMapping)) {
            log.info("注册子模块失败,不是特定的模块信息");
            return this;
        }
        putIfAbsent(moduleMapping, module);
        return this;
    }

    @Override
    public <T extends Module> T getModule(ModuleMapping moduleMapping) {
        return (T) getOrDefault(moduleMapping, null);
    }
}
