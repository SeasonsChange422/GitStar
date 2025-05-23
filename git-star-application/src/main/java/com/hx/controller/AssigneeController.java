package com.hx.controller;

import com.hx.aop.annotation.ProjectAuth;
import com.hx.aop.annotation.UserAuth;
import com.hx.common.Result;
import com.hx.enums.CheckObjectEnum;
import com.hx.service.AssigneeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author dhx
 * @date 2025/5/16 1:35
 */
@RestController
public class AssigneeController {
    @Autowired
    private AssigneeService assigneeService;
    @UserAuth
    @ProjectAuth(checkOperability = true,checkObject = CheckObjectEnum.CHECK_TASK)
    @PostMapping("/api/assignee/claimTask")
    public Result claimTask(@RequestParam Long taskId,@RequestHeader("token") String token){
        return assigneeService.claimTask(taskId,token);
    }

    @UserAuth
    @ProjectAuth(checkOperability = true,checkObject = CheckObjectEnum.CHECK_TASK)
    @GetMapping("/api/assignee/getAssigneeOfTask")
    public Result getAssigneeOfTask(@RequestParam Long taskId){
        return assigneeService.getAssigneeOfTask(taskId);
    }
}
