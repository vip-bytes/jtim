package cn.bytes.jtim.core.module;

import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @date 2020/2/16 21:23
 */
@Slf4j
public abstract class AbstractSimpleModule extends HashMap<ModuleSlot, Module> implements Module {

    private Module host;

    @Override
    public Module host(Module host) {
        this.host = host;
        return this;
    }

    @Override
    public <T extends Module> T getHost() {
        return (T) this.host;
    }

    @Override
    public Module boarder(Module... boarders) {
        if (Objects.isNull(boarders)) {
            log.warn("模块信息为空");
            return this;
        }
        Stream.of(boarders).forEach(this::boarder);
        return this;
    }

    @Override
    public Collection<Module> getBoarders() {
        return this.values();
    }

    private void boarder(Module boarder) {
        if (Objects.isNull(boarder)) {
            return;
        }
        log.info(">>>[{}] add slot [{}]>>>", this.getClass().getSimpleName(), boarder.mapping());
        boarder.host(this);
        this.putIfAbsent(boarder.mapping(), boarder);
    }

    @Override
    public <T extends Module> T getBoarder(ModuleSlot mapping) {
        return (T) this.getOrDefault(mapping, null);
    }
}
