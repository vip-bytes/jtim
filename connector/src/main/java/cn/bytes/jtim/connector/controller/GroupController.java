package cn.bytes.jtim.connector.controller;

import cn.bytes.jtim.core.service.GroupService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 分组创建
 *
 * @version 1.0
 * @date 2020/3/17 9:12
 */
@RestController
@RequestMapping("/group/v1")
public class GroupController {

    @Reference
    private GroupService groupService;

}
