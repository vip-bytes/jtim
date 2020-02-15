package cn.bytes.jtim.core.module;

/**
 * @version 1.0
 * @date 2020/2/15 15:41
 */
public interface Module {

    default Module add(Module module) {
        return this;
    }

    default <T extends Module> T getModule(ModuleMapping moduleMapping) {
        return null;
    }

    default ModuleMapping getModuleMapping() {
        return null;
    }

}
