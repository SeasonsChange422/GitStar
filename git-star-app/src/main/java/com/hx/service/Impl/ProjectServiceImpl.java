package com.hx.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hx.common.Result;
import com.hx.common.enums.AppHttpCodeEnum;
import com.hx.common.enums.UserProjectAuthEnum;
import com.hx.entity.DTO.project.NewProjectDTO;
import com.hx.entity.DTO.project.UpdateProjectDTO;
import com.hx.entity.PO.Comment;
import com.hx.entity.PO.Project;
import com.hx.entity.PO.relationship.PostComment;
import com.hx.entity.PO.relationship.UserProject;
import com.hx.mapper.ProjectMapper;
import com.hx.mapper.relationship.UserProjectMapper;
import com.hx.service.ProjectService;
import com.hx.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dhx
 * @date 2025/1/8 16:33
 */
@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectMapper projectMapper;
    @Autowired
    private UserProjectMapper userProjectMapper;

    @Override
    public Result newProject(NewProjectDTO newProjectDTO,String token) {
        Project project = new Project(newProjectDTO.getName(),newProjectDTO.getVisibility());
        if(projectMapper.insert(project)==1&&userProjectMapper.insert(new UserProject(JwtUtil.getUserIdFromToken(token),project.getId(), UserProjectAuthEnum.ADMINISTRATOR))==1){
            return Result.okResult(200,"新建成功");
        }
        return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }
    @Override
    public Result delProject(Long projectId) {
        if(userProjectMapper.delete(new QueryWrapper<UserProject>().eq("project_id",projectId))!=0&&projectMapper.deleteById(projectId)==1){
            return Result.okResult(200,"删除成功");
        }
        return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }
    @Override
    public Result updateProject(UpdateProjectDTO updateProjectDTO) {
        Project project = projectMapper.selectById(updateProjectDTO.getProjectId());
        project.setName(updateProjectDTO.getName());
        project.setVisibility(updateProjectDTO.getVisibility());
        if(projectMapper.updateById(project)==1){
            return Result.okResult(200,"修改成功");
        }
        return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }
    @Override
    public Result getProjectInfo(Long projectId) {
        return Result.okResult(projectMapper.selectById(projectId));
    }

    @Override
    public Result getProjectList(Page<Project> page) {
        Page<Project> projectPage = projectMapper.selectPage(page,new QueryWrapper<Project>().eq("project_visibility",true));
        return Result.okResult(projectPage);
    }

    @Override
    public Result getProjectListByToken(Page<Project> page,String token) {
        Long userId = JwtUtil.getUserIdFromToken(token);
        List<UserProject> list = userProjectMapper.selectList(new QueryWrapper<UserProject>().eq("user_id",userId));
        List<Long> projectIds = list.stream()
                .map(UserProject::getProjectId)
                .collect(Collectors.toList());
        if (projectIds.isEmpty()) {
            return Result.okResult(new ArrayList<>());
        }
        Page<Project> projects = projectMapper.selectPage(page,new QueryWrapper<Project>()
                .in("project_id", projectIds));
        return Result.okResult(projects);
    }
}
