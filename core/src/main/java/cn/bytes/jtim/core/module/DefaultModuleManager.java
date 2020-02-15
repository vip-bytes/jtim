package cn.bytes.jtim.core.module;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Objects;

/**
 * @version 1.0
 * @date 2020/2/15 15:48
 */
@Slf4j
@Builder
public class DefaultModuleManager extends HashMap<ModuleMapping, Module> implements ModuleManager {

    @Override
    public ModuleManager module(Module module) {

        if (Objects.isNull(module)) {
            log.warn("模块信息为空");
            return this;
        }

        ModuleMapping moduleMapping = module.getModuleMapping();
        if (Objects.isNull(moduleMapping)) {
            log.warn("模块信息【moduleMapping】为空");
            return this;
        }
        putIfAbsent(moduleMapping, module);
        return this;
    }

    @Override
    public <T extends Module> T getModule(ModuleMapping moduleMapping) {
        return (T) get(moduleMapping);
    }

}
