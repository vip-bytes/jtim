package cn.bytes.jtim.core.service;

import cn.bytes.jtim.core.protocol.protobuf.DefineResponse;

/**
 * 群操作处理
 *
 * @version 1.0
 * @date 2020/3/2 16:53
 */
public interface GroupService {

    DefineResponse create(cn.bytes.jtim.core.protocol.protobuf.CreateRequest createRequest);

    DefineResponse remove(cn.bytes.jtim.core.protocol.protobuf.RemoveRequest removeRequest);

    DefineResponse forbidden(cn.bytes.jtim.core.protocol.protobuf.ForbiddenRequest forbiddenRequest);

    DefineResponse unForbidden(cn.bytes.jtim.core.protocol.protobuf.UnForbiddenRequest unForbiddenRequest);

    DefineResponse join(cn.bytes.jtim.core.protocol.protobuf.JoinRequest joinRequest);

    DefineResponse leave(cn.bytes.jtim.core.protocol.protobuf.LeaveRequest leaveRequest);

    DefineResponse kick(cn.bytes.jtim.core.protocol.protobuf.KickRequest kickRequest);

}
