package cn.bytes.jtim.core.module;

import cn.bytes.jtim.core.config.Configuration;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Objects;

/**
 * @date 2020/2/16 21:23
 */
@Slf4j
@Getter
public abstract class AbstractSimpleModule extends HashMap<String, Module> implements Module {

    private Module host;

    private Configuration configuration;

    @Override
    public <T extends Module> Module then(Class<T> key, Module module) {
        if (Objects.nonNull(key) && Objects.nonNull(module)) {
            module.host(this);
            module.configuration(this.configuration);

            final String moduleKey = key.getSimpleName();
            log.info("add module: this=[{}] key=[{}],value=[{}]",
                    this.getClass().getSimpleName(), moduleKey, module.getClass().getSimpleName());
            this.put(moduleKey, module);
        }
        return this;
    }

    @Override
    public <T extends Module> T getModule(String key) {
        return this.getModule(this, key);
    }

    @Override
    public <T extends Module> T getModule(Class<T> key) {
        if (Objects.isNull(key)) {
            return null;
        }
        return this.getModule(key.getSimpleName());
    }

    private <T extends Module> T getModule(Module module, String key) {

        if (Objects.isNull(module) || StringUtils.isBlank(key)) {
            return (T) null;
        }
        Module thatModule = module.getOrDefault(key, null);
        if (Objects.nonNull(thatModule)) {
            return (T) thatModule;
        }
        return getModule(module.getHost(), key);
    }

    @Override
    public void host(Module host) {
        this.host = host;
    }

    @Override
    public void configuration(Configuration configuration) {
        this.configuration = configuration;
    }

}
