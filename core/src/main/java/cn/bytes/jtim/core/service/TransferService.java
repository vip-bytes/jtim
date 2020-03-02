package cn.bytes.jtim.core.service;

import cn.bytes.jtim.core.protocol.protobuf.O2MSendRequest;
import cn.bytes.jtim.core.protocol.protobuf.O2MSendResponse;
import cn.bytes.jtim.core.protocol.protobuf.O2OSendRequest;
import cn.bytes.jtim.core.protocol.protobuf.O2OSendResponse;

/**
 * 传输通信服务
 * <p>
 * 1. o2o 传输 一对一
 * 2. o2m 传输 一对多
 * </p>
 *
 * @version 1.0
 * @date 2020/3/2 14:08
 */
public interface TransferService {

    /**
     * 一对一传输
     *
     * @param o2OSendRequest
     * @return
     */
    O2OSendResponse transfer(O2OSendRequest o2OSendRequest);

    /**
     * 一对多传输
     *
     * @param o2MSendRequest
     * @return
     */
    O2MSendResponse transfer(O2MSendRequest o2MSendRequest);

}
