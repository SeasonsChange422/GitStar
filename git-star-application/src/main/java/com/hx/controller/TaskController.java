package com.hx.controller;

import com.hx.aop.annotation.ProjectAuth;
import com.hx.aop.annotation.UserAuth;
import com.hx.common.Result;
import com.hx.enums.CheckObjectEnum;
import com.hx.entity.DTO.task.CloseTask;
import com.hx.entity.DTO.task.NewTask;
import com.hx.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author dhx
 * @date 2025/5/15 18:34
 */
@RestController
public class TaskController {
    @Autowired
    private TaskService taskService;
    @UserAuth
    @ProjectAuth(checkOperability = true,checkObject = CheckObjectEnum.CHECK_REPOSITORY)
    @PostMapping("/api/task/newTask")
    public Result newTask(@RequestBody NewTask newTaskForm){
        return taskService.newTask(newTaskForm);
    }
    @UserAuth
    @ProjectAuth(checkOperability = true,checkObject = CheckObjectEnum.CHECK_REPOSITORY)
    @PostMapping("/api/task/closeTask")
    public Result closeTask(@RequestBody CloseTask closeTaskType){
        return taskService.closeTask(closeTaskType);
    }
    @UserAuth
    @ProjectAuth(checkVisibility = true,checkObject = CheckObjectEnum.CHECK_REPOSITORY)
    @GetMapping("/api/task/getTaskList")
    public Result getTaskList(@RequestParam Long repositoryId){
        return taskService.getTaskList(repositoryId);
    }
    @UserAuth
    @ProjectAuth(checkVisibility = true,checkObject = CheckObjectEnum.CHECK_REPOSITORY)
    @GetMapping("/api/task/getClosedTaskList")
    public Result getClosedTaskList(@RequestParam Long repositoryId){
        return taskService.getClosedTaskList(repositoryId);
    }
    @UserAuth
    @GetMapping("/api/task/getTaskInfo")
    public Result getTaskInfo(@RequestParam Long taskId){
        return taskService.getTaskInfo(taskId);
    }
    @UserAuth
    @GetMapping("/api/task/getRepositoryOfTask")
    public Result getRepositoryOfTask(@RequestParam Long taskId){
        return taskService.getRepositoryOfTask(taskId);
    }
}
