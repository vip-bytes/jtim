package cn.bytes.jtim.logic.service;

import cn.bytes.jtim.core.protocol.protobuf.DefineResponse;

/**
 * @version 1.0
 * @date 2020/3/10 10:55
 */
public class BasicService {

    public DefineResponse responseOK() {
        return DefineResponse.newBuilder()
                .setResult(true)
                .setTs(System.currentTimeMillis())
                .setMessage("操作成功")
                .build();
    }
}
