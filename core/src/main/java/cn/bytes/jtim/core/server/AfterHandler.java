package cn.bytes.jtim.core.server;

import java.util.function.Consumer;

/**
 * @version 1.0
 * @date 2020/2/12 14:21
 */
public interface AfterHandler<T> extends Consumer<T> {
}
