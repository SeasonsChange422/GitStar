package com.hx.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hx.common.Result;
import com.hx.common.enums.AppHttpCodeEnum;
import com.hx.enums.TaskCloseTypeEnum;
import com.hx.entity.DTO.task.CloseTask;
import com.hx.entity.DTO.task.NewTask;
import com.hx.entity.PO.Task;
import com.hx.entity.PO.relationship.RepositoryTask;
import com.hx.mapper.RepositoryMapper;
import com.hx.mapper.TaskMapper;
import com.hx.mapper.relationship.RepositoryTaskMapper;
import com.hx.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dhx
 * @date 2025/5/15 18:06
 */
@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private RepositoryTaskMapper repositoryTaskMapper;
    @Autowired
    private RepositoryMapper repositoryMapper;
    @Override
    public Result newTask(NewTask newTaskForm) {
        try {
            Task task = new Task(newTaskForm.getTitle());
            taskMapper.insert(task);
            repositoryTaskMapper.insert(new RepositoryTask(newTaskForm.getRepositoryId(), task.getId()));
            return Result.okResult(task);
        } catch (Exception e){
            e.printStackTrace();
            return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public Result closeTask(CloseTask closeTaskForm) {
        try {
            Task task = taskMapper.selectById(closeTaskForm.getTaskId());
            task.setType(TaskCloseTypeEnum.valueOf(closeTaskForm.getType().toUpperCase()));
            task.setClosed(true);
            taskMapper.updateById(task);
            return Result.okResult();
        } catch (Exception e){
            e.printStackTrace();
            return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public Result getTaskList(Long repositoryId) {
        try {
            List<Long> taskIds = repositoryTaskMapper.selectList(new QueryWrapper<RepositoryTask>().eq("repository_id",repositoryId)).
                    stream().map(RepositoryTask::getTaskId).collect(Collectors.toList());
            List<Task> tasks = taskMapper.selectList(new QueryWrapper<Task>().in("task_id",taskIds).eq("closed",false));
            return Result.okResult(tasks);
        } catch (Exception e){
            e.printStackTrace();
            return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public Result getClosedTaskList(Long repositoryId) {
        try {
            List<Long> taskIds = repositoryTaskMapper.selectList(new QueryWrapper<RepositoryTask>().eq("repository_id",repositoryId)).
                    stream().map(RepositoryTask::getTaskId).collect(Collectors.toList());
            List<Task> tasks = taskMapper.selectList(new QueryWrapper<Task>().in("task_id",taskIds).eq("closed",true));
            return Result.okResult(tasks);
        } catch (Exception e){
            e.printStackTrace();
            return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public Result getTaskInfo(Long taskId) {
        try {
            return Result.okResult(taskMapper.selectById(taskId));
        } catch (Exception e){
            return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public Result getRepositoryOfTask(Long taskId) {
        try {
            RepositoryTask repositoryTask = repositoryTaskMapper.selectOne(new QueryWrapper<RepositoryTask>().eq("task_id",taskId));
            if(repositoryTask==null){
                return Result.okResult(null);
            }
            return Result.okResult(repositoryMapper.selectById(repositoryTask.getRepositoryId()));
        } catch (Exception e){
            return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }
}
