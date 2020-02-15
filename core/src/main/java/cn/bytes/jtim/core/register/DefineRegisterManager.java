package cn.bytes.jtim.core.register;

import cn.bytes.jtim.core.module.Module;
import cn.bytes.jtim.core.module.ModuleMapping;
import cn.bytes.jtim.core.retry.DefineRetryManager;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 注册中心管理,即连接管理器
 *
 * @version 1.0
 * @date 2020/2/15 11:34
 */
public interface DefineRegisterManager extends Map<ModuleMapping, Module>, Module {

    default boolean register(RegisterNode registerNode) {
        return this.register(registerNode, getModule(ModuleMapping.MODULE_RETRY_MANAGER));
    }

    boolean register(RegisterNode registerNode, DefineRetryManager defineRetryManager);

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    class RegisterNode {

        private String key;

        private String host;

        private int port;

    }

}
