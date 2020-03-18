package cn.bytes.jtim.logic.service.rpc;

import cn.bytes.jtim.core.protocol.protobuf.*;
import cn.bytes.jtim.core.service.GroupService;
import org.apache.dubbo.config.annotation.Service;

/**
 * 群操作处理
 *
 * @version 1.0
 * @date 2020/3/9 10:30
 */
@Service
public class GroupServiceImpl extends BasicRpcService implements GroupService {

    @Override
    public DefineResponse create(CreateRequest createRequest) {
        return responseOK();
    }

    @Override
    public DefineResponse remove(RemoveRequest removeRequest) {
        return null;
    }

    @Override
    public DefineResponse forbidden(ForbiddenRequest forbiddenRequest) {
        return null;
    }

    @Override
    public DefineResponse unForbidden(UnForbiddenRequest unForbiddenRequest) {
        return null;
    }

    @Override
    public DefineResponse join(JoinRequest joinRequest) {
        return null;
    }

    @Override
    public DefineResponse leave(LeaveRequest leaveRequest) {
        return null;
    }

    @Override
    public DefineResponse kick(KickRequest kickRequest) {
        return null;
    }

}
