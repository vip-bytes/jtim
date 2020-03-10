package cn.bytes.jtim.logic.service.rpc;

import cn.bytes.jtim.core.protocol.protobuf.O2MSendRequest;
import cn.bytes.jtim.core.protocol.protobuf.O2MSendResponse;
import cn.bytes.jtim.core.protocol.protobuf.O2OSendRequest;
import cn.bytes.jtim.core.protocol.protobuf.O2OSendResponse;
import cn.bytes.jtim.core.service.TransferService;
import cn.bytes.jtim.logic.service.RouteService;
import org.apache.dubbo.config.annotation.Service;

/**
 * 数据传输处理
 *
 * @version 1.0
 * @date 2020/3/9 10:33
 */
@Service
public class TransferServiceImpl implements TransferService {

    private RouteService routeService;

    public TransferServiceImpl(RouteService routeService) {
        this.routeService = routeService;
    }

    @Override
    public O2OSendResponse transfer(O2OSendRequest o2OSendRequest) {


        return null;
    }

    @Override
    public O2MSendResponse transfer(O2MSendRequest o2MSendRequest) {
        return null;
    }
}
