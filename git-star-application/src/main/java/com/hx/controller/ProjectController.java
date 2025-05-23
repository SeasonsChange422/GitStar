package com.hx.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hx.aop.annotation.ProjectAuth;
import com.hx.aop.annotation.UserAuth;
import com.hx.common.Result;
import com.hx.entity.DTO.project.DelProjectDTO;
import com.hx.entity.DTO.project.NewProjectDTO;
import com.hx.entity.DTO.project.UpdateProjectDTO;
import com.hx.entity.PO.Project;
import com.hx.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author dhx
 * @date 2025/1/8 16:24
 */
@RestController
public class ProjectController {
    @Autowired
    private ProjectService projectService;
    @UserAuth
    @PostMapping("/api/project/newProject")
    public Result newProject(@RequestBody NewProjectDTO newProjectDTO, @RequestHeader("token")String token){
        return projectService.newProject(newProjectDTO,token);
    }
    @UserAuth
    @ProjectAuth(checkOperability = true)
    @PostMapping("/api/project/delProject")
    public Result delProject(@RequestBody DelProjectDTO delProjectDTO){
        return projectService.delProject(delProjectDTO.getProjectId());
    }
    @UserAuth
    @ProjectAuth(checkOperability = true)
    @PostMapping("/api/project/updateProject")
    public Result updateProject(@RequestBody UpdateProjectDTO updateProjectDTO){
        return projectService.updateProject(updateProjectDTO);
    }
    @ProjectAuth(checkVisibility = true)
    @GetMapping("/api/project/getProjectInfo")
    public Result getProjectInfo(@RequestParam Long projectId){
        return projectService.getProjectInfo(projectId);
    }
    @PostMapping("/api/project/getProjectList")
    public Result getProjectList(@RequestBody Page<Project> page){return projectService.getProjectList(page);}

    @UserAuth
    @PostMapping("/api/project/getProjectByToken")
    public Result getProjectByToken(@RequestBody Page<Project> page,@RequestHeader("token")String token){return projectService.getProjectListByToken(page,token);}

    @GetMapping("/api/project/search")
    public Result searchProject(@RequestParam String key) {
        return projectService.searchProject(key);
    }

    @GetMapping("/api/project/getUserOfProject")
    public Result getUserOfProject(@RequestParam Long projectId) {
        return projectService.getUserOfProject(projectId);
    }

    @GetMapping("/api/project/getPostOfProject")
    public Result getPostOfProject(@RequestParam Long projectId) {
        return projectService.getPostOfProject(projectId);
    }
}
