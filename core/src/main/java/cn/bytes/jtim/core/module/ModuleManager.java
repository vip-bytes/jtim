package cn.bytes.jtim.core.module;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author maliang@sioniov.com
 * @version 1.0
 * @date 2020/2/15 16:23
 */
public interface ModuleManager extends Map<ModuleMapping, Module> {

    ModuleManager module(Module module);

    default ModuleManager module(Module... modules) {
        if (Objects.nonNull(modules) && modules.length > 0) {
            Stream.of(modules).forEach(this::module);
        }
        return this;
    }

    <T extends Module> T getModule(ModuleMapping moduleMapping);

}
