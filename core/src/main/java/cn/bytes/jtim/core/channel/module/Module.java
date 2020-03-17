package cn.bytes.jtim.core.channel.module;

import cn.bytes.jtim.core.channel.config.Configuration;

import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 模块，即每个功能相依的另一个功能模块
 * </p>
 *
 * @version 1.0
 * @date 2020/2/16 21:09
 */
public interface Module extends Map<String, Module>, ModuleWare {

    <T extends Module> T getHost();

    Configuration getConfiguration();

    <T extends Module> Module then(Module module);

    <T extends Module> T getModule(String key);

    default <T extends Module> T getModule(Class<T> kClass) {
        return getModule(Objects.isNull(kClass) ? null : kClass.getSimpleName());
    }

    String key();

}
