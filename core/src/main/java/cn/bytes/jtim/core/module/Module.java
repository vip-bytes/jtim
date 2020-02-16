package cn.bytes.jtim.core.module;

import java.util.Collection;
import java.util.Map;

/**
 * <p>
 * 模块，即每个功能相依的另一个功能模块
 * </p>
 *
 * @version 1.0
 * @date 2020/2/16 21:09
 */
public interface Module extends Map<ModuleMapping, Module> {

    /**
     * 模块映射名称
     *
     * @return
     */
    ModuleMapping mapping();

    /**
     * 添加寄宿主
     *
     * @param host
     * @return
     */
    Module host(Module host);

    /**
     * 获取寄主主
     *
     * @param <T>
     * @return
     */
    <T extends Module> T getHost();

    /**
     * 添加寄宿者
     *
     * @param boarders
     * @return
     */
    Module boarder(Module... boarders);

    /**
     * 获取寄宿者
     *
     * @return
     */
    Collection<Module> getBoarders();

    <T extends Module> T getBoarder(ModuleMapping mapping);
}
