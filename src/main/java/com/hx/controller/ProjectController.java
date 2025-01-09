package com.hx.controller;

import com.hx.aop.annotation.ProjectAuth;
import com.hx.aop.annotation.UserAuth;
import com.hx.common.Result;
import com.hx.entity.DTO.project.DelProjectDTO;
import com.hx.entity.DTO.project.NewProjectDTO;
import com.hx.entity.DTO.project.UpdateProjectDTO;
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
    @ProjectAuth(operability = true)
    @PostMapping("/api/project/delProject")
    public Result delProject(@RequestBody DelProjectDTO delProjectDTO){
        return projectService.delProject(delProjectDTO.getProjectId());
    }
    @UserAuth
    @ProjectAuth(operability = true)
    @PostMapping("/api/project/updateProject")
    public Result updateProject(@RequestBody UpdateProjectDTO updateProjectDTO){
        return projectService.updateProject(updateProjectDTO);
    }
    @ProjectAuth(visibility = true)
    @GetMapping("/api/project/getProjectInfo")
    public Result getProjectInfo(@RequestParam Long projectId){
        return projectService.getProjectInfo(projectId);
    }
}
