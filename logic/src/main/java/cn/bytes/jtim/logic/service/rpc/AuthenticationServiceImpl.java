package cn.bytes.jtim.logic.rpc;

import cn.bytes.jtim.core.protocol.protobuf.AuthRequest;
import cn.bytes.jtim.core.protocol.protobuf.AuthResponse;
import cn.bytes.jtim.core.protocol.protobuf.LogoutRequest;
import cn.bytes.jtim.core.protocol.protobuf.LogoutResponse;
import cn.bytes.jtim.core.service.AuthenticationService;

/**
 * 认证处理
 *
 * @version 1.0
 * @date 2020/3/9 10:31
 */
public class AuthenticationServiceImpl implements AuthenticationService {
    @Override
    public AuthResponse identification(AuthRequest authRequest) {
        return null;
    }

    @Override
    public LogoutResponse destroy(LogoutRequest logoutRequest) {
        return null;
    }
}
