package com.hx.service;

import com.hx.common.Result;

/**
 * @author dhx
 * @date 2025/5/16 1:25
 */
public interface AssigneeService {
    public Result claimTask(Long taskId,String token);
    public Result getAssigneeOfTask(Long taskId);
}
