package cn.bytes.jtim.core.service;

import cn.bytes.jtim.core.protocol.protobuf.DefineResponse;

/**
 * 群操作处理
 *
 * @version 1.0
 * @date 2020/3/2 16:53
 */
public interface GroupService {

    /**
     * 创建群信息
     *
     * @param createRequest
     * @return cn.bytes.jtim.core.protocol.protobuf.DefineResponse
     **/
    DefineResponse create(cn.bytes.jtim.core.protocol.protobuf.CreateRequest createRequest);

    /**
     * 删除群信息
     *
     * @param removeRequest
     * @return cn.bytes.jtim.core.protocol.protobuf.DefineResponse
     **/
    DefineResponse remove(cn.bytes.jtim.core.protocol.protobuf.RemoveRequest removeRequest);

    /**
     * 禁用群用户信息
     *
     * @param forbiddenRequest
     * @return cn.bytes.jtim.core.protocol.protobuf.DefineResponse
     **/
    DefineResponse forbidden(cn.bytes.jtim.core.protocol.protobuf.ForbiddenRequest forbiddenRequest);

    /**
     * 取消禁用
     *
     * @param unForbiddenRequest
     * @return cn.bytes.jtim.core.protocol.protobuf.DefineResponse
     **/
    DefineResponse unForbidden(cn.bytes.jtim.core.protocol.protobuf.UnForbiddenRequest unForbiddenRequest);

    /**
     * 加入群消息
     *
     * @param joinRequest
     * @return cn.bytes.jtim.core.protocol.protobuf.DefineResponse
     **/
    DefineResponse join(cn.bytes.jtim.core.protocol.protobuf.JoinRequest joinRequest);

    /**
     * 离开群消息
     *
     * @param leaveRequest
     * @return cn.bytes.jtim.core.protocol.protobuf.DefineResponse
     **/
    DefineResponse leave(cn.bytes.jtim.core.protocol.protobuf.LeaveRequest leaveRequest);

    /**
     * 踢出群
     *
     * @param kickRequest
     * @return cn.bytes.jtim.core.protocol.protobuf.DefineResponse
     **/
    DefineResponse kick(cn.bytes.jtim.core.protocol.protobuf.KickRequest kickRequest);

}
