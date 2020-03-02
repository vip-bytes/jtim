package cn.bytes.jtim.core.service;

import cn.bytes.jtim.core.protocol.protobuf.O2MDistributeRequest;
import cn.bytes.jtim.core.protocol.protobuf.O2MDistributeResponse;
import cn.bytes.jtim.core.protocol.protobuf.O2ODistributeRequest;
import cn.bytes.jtim.core.protocol.protobuf.O2ODistributeResponse;

/**
 * 分发处理
 *
 * @version 1.0
 * @date 2020/3/2 16:47
 */
public interface DistributeService {

    /**
     * 一对一消息分发
     *
     * @param o2ODistributeRequest
     * @return
     */
    O2ODistributeResponse distribute(O2ODistributeRequest o2ODistributeRequest);

    /**
     * 一对多消息分发
     *
     * @param o2MDistributeRequest
     * @return
     */
    O2MDistributeResponse distribute(O2MDistributeRequest o2MDistributeRequest);
}
