package com.hx.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hx.aop.annotation.ProjectAuth;
import com.hx.aop.annotation.UserAuth;
import com.hx.common.Result;
import com.hx.common.enums.CheckObjectEnum;
import com.hx.entity.DTO.repository.DelRepositoryDTO;
import com.hx.entity.DTO.repository.NewRepositoryDTO;
import com.hx.entity.DTO.repository.UpdateRepositoryDTO;
import com.hx.entity.PO.Repository;
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
    @ProjectAuth(checkOperability = true,checkObject = CheckObjectEnum.CHECK_REPOSITORY)
    @PostMapping("/api/repository/newRepository")
    public Result newRepository(@RequestBody NewRepositoryDTO newRepositoryDTO){
        return repositoryService.newRepository(newRepositoryDTO);
    }
    @UserAuth
    @ProjectAuth(checkOperability = true,checkObject = CheckObjectEnum.CHECK_REPOSITORY)
    @PostMapping("/api/repository/delRepository")
    public Result delRepository(@RequestBody DelRepositoryDTO delRepositoryDTO){
        return repositoryService.delRepository(delRepositoryDTO.getRepositoryId());
    }
    @UserAuth
    @ProjectAuth(checkOperability = true,checkObject = CheckObjectEnum.CHECK_REPOSITORY)
    @PostMapping("/api/repository/updateRepository")
    public Result updateRepository(@RequestBody UpdateRepositoryDTO updateRepositoryDTO){
        return repositoryService.updateRepository(updateRepositoryDTO);
    }
    @ProjectAuth(checkVisibility = true,checkObject = CheckObjectEnum.CHECK_REPOSITORY)
    @GetMapping("/api/repository/getRepositoryInfo")
    public Result getRepositoryInfo(@RequestParam Long repositoryId){
        return repositoryService.getRepositoryInfo(repositoryId);
    }
    @GetMapping("/api/repository/getRepositoryList")
    public Result getRepositoryList(@RequestBody Page<Repository> page){
        return repositoryService.getRepositoryList(page);
    }
    @ProjectAuth(checkVisibility = true,checkObject = CheckObjectEnum.CHECK_PROJECT)
    @GetMapping("/api/repository/getRepositoryListByProjectId")
    public Result getRepositoryListByProjectId(@RequestBody Page<Repository> page,@RequestParam Long projectId){
        return repositoryService.getRepositoryListByProjectId(page,projectId);
    }
}
