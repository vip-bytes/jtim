package cn.bytes.jtim.core.connection;

import java.util.Map;

/**
 * @version 1.0
 * @date 2020/2/12 0:10
 */
public interface ConnectionManager<K,V extends Connection> extends Map<K,V> {

    /**
     * 保存连接信息
     * @param v
     */
    void saveConnection(V v);

    /**
     * 根据连接编号写入数据
     * @param channelId
     * @param body
     */
    void writeAndFlush(K channelId, Object body);



}
