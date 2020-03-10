package cn.bytes.jtim.logic.service.rpc;

import cn.bytes.jtim.core.protocol.protobuf.O2MDistributeRequest;
import cn.bytes.jtim.core.protocol.protobuf.O2MDistributeResponse;
import cn.bytes.jtim.core.protocol.protobuf.O2ODistributeRequest;
import cn.bytes.jtim.core.protocol.protobuf.O2ODistributeResponse;
import cn.bytes.jtim.core.service.DistributeService;

/**
 * 分发处理
 *
 * @version 1.0
 * @date 2020/3/9 10:32
 */
@Deprecated
public class DistributeServiceImpl implements DistributeService {
    @Override
    public O2ODistributeResponse distribute(O2ODistributeRequest o2ODistributeRequest) {
        return null;
    }

    @Override
    public O2MDistributeResponse distribute(O2MDistributeRequest o2MDistributeRequest) {
        return null;
    }
}
