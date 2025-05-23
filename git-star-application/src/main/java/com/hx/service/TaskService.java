package com.hx.service;

import com.hx.common.Result;
import com.hx.entity.DTO.task.CloseTask;
import com.hx.entity.DTO.task.NewTask;

/**
 * @author dhx
 * @date 2025/5/15 18:06
 */
public interface TaskService {
    public Result newTask(NewTask newTaskForm);
    public Result closeTask(CloseTask closeTaskType);
    public Result getTaskList(Long repositoryId);
    public Result getClosedTaskList(Long repositoryId);
    public Result getTaskInfo(Long taskId);
    public Result getRepositoryOfTask(Long taskId);
}
