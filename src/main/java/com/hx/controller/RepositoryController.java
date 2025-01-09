package com.hx.controller;

import com.hx.aop.annotation.ProjectAuth;
import com.hx.aop.annotation.UserAuth;
import com.hx.common.Result;
import com.hx.common.enums.CheckObjectEnum;
import com.hx.entity.DTO.repository.DelRepositoryDTO;
import com.hx.entity.DTO.repository.NewRepositoryDTO;
import com.hx.entity.DTO.repository.UpdateRepositoryDTO;
import com.hx.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author dhx
 * @date 2025/1/8 16:24
 */
@RestController
public class RepositoryController {
    @Autowired
    private RepositoryService repositoryService;
    @UserAuth
    @PostMapping("/api/repository/newRepository")
    public Result newProject(@RequestBody NewRepositoryDTO newRepositoryDTO, @RequestHeader("token")String token){
        return repositoryService.newRepository(newRepositoryDTO,token);
    }
    @UserAuth
    @ProjectAuth(checkOperability = true,checkObject = CheckObjectEnum.CHECK_REPOSITORY)
    @PostMapping("/api/repository/delRepository")
    public Result delProject(@RequestBody DelRepositoryDTO delRepositoryDTO){
        return repositoryService.delRepository(delRepositoryDTO.getRepositoryId());
    }
    @UserAuth
    @ProjectAuth(checkOperability = true,checkObject = CheckObjectEnum.CHECK_REPOSITORY)
    @PostMapping("/api/repository/updateRepository")
    public Result updateProject(@RequestBody UpdateRepositoryDTO updateRepositoryDTO){
        return repositoryService.updateRepository(updateRepositoryDTO);
    }
    @ProjectAuth(checkVisibility = true,checkObject = CheckObjectEnum.CHECK_REPOSITORY)
    @GetMapping("/api/repository/getRepositoryInfo")
    public Result getProjectInfo(@RequestParam Long projectId){
        return repositoryService.getRepositoryInfo(projectId);
    }
}
