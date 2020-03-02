package cn.bytes.jtim.core.service;

import cn.bytes.jtim.core.protocol.protobuf.AuthRequest;
import cn.bytes.jtim.core.protocol.protobuf.AuthResponse;
import cn.bytes.jtim.core.protocol.protobuf.LogoutRequest;
import cn.bytes.jtim.core.protocol.protobuf.LogoutResponse;

/**
 * 认证服务
 *
 * @version 1.0
 * @date 2020/3/2 13:58
 */
public interface AuthenticationService {

    /**
     * 连接认证
     *
     * @param authRequest
     * @return
     */

    AuthResponse identification(AuthRequest authRequest);

    /**
     * 认证销毁
     *
     * @param logoutRequest
     * @return
     */
    LogoutResponse destroy(LogoutRequest logoutRequest);


}
