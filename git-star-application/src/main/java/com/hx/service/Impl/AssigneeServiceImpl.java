package com.hx.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hx.common.Result;
import com.hx.common.enums.AppHttpCodeEnum;
import com.hx.entity.PO.relationship.TaskAssignee;
import com.hx.mapper.TaskMapper;
import com.hx.mapper.UserMapper;
import com.hx.mapper.relationship.TaskAssigneeMapper;
import com.hx.service.AssigneeService;
import com.hx.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dhx
 * @date 2025/5/16 1:25
 */
@Service
public class AssigneeServiceImpl implements AssigneeService {
    @Autowired
    private TaskAssigneeMapper taskAssigneeMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public Result claimTask(Long taskId, String token) {
        try {
            Long userId = JwtUtil.getUserIdFromToken(token);
            TaskAssignee taskAssignee = taskAssigneeMapper.selectOne(new QueryWrapper<TaskAssignee>().eq("task_id",taskId));
            if(taskAssignee!=null){
                taskAssigneeMapper.delete(new QueryWrapper<TaskAssignee>().eq("task_id",taskAssignee.getTaskId()).eq("user_id",taskAssignee.getUserId()));
                return Result.okResult();
            }
            taskAssignee = new TaskAssignee(taskId,userId);
            taskAssigneeMapper.insert(taskAssignee);
            return Result.okResult();
        } catch (Exception e){
            e.printStackTrace();
            return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public Result getAssigneeOfTask(Long taskId) {
        try {
            TaskAssignee taskAssignee = taskAssigneeMapper.selectOne(new QueryWrapper<TaskAssignee>().eq("task_id",taskId));
            if(taskAssignee==null){
                return Result.okResult(null);
            }
            return Result.okResult(userMapper.selectById(taskAssignee.getUserId()));
        } catch (Exception e){
            e.printStackTrace();
            return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }
}
