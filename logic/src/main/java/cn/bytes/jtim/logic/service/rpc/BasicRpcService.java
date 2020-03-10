package cn.bytes.jtim.logic.rpc;

import cn.bytes.jtim.core.protocol.protobuf.DefineResponse;

/**
 *
 */
public class BasicRpcService {

    public DefineResponse responseOK() {
        return DefineResponse.newBuilder()
                .setResult(true)
                .setTs(System.currentTimeMillis())
                .setMessage("操作成功")
                .build();
    }
}
