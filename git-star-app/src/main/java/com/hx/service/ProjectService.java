package com.hx.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hx.common.Result;
import com.hx.entity.DTO.project.NewProjectDTO;
import com.hx.entity.DTO.project.UpdateProjectDTO;
import com.hx.entity.PO.Project;

/**
 * @author dhx
 * @date 2025/1/8 16:32
 */
public interface ProjectService {
    public Result newProject(NewProjectDTO newProjectDTO,String token);
    public Result delProject(Long projectId);
    public Result updateProject(UpdateProjectDTO updateProjectDTO);
    public Result getProjectInfo(Long projectId);
    public Result getProjectList(Page<Project> page);
    public Result getProjectListByToken(Page<Project> page,String token);
}
