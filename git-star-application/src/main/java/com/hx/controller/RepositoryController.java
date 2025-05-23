package com.hx.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hx.aop.annotation.ProjectAuth;
import com.hx.aop.annotation.UserAuth;
import com.hx.common.Result;
import com.hx.enums.CheckObjectEnum;
import com.hx.entity.DTO.repository.DelRepositoryDTO;
import com.hx.entity.DTO.repository.NewRepositoryDTO;
import com.hx.entity.DTO.repository.UpdateRepositoryDTO;
import com.hx.entity.PO.Repository;
import com.hx.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public Result getRepositoryInfo(@RequestParam Long repositoryId,@RequestHeader("token")String token){
        return repositoryService.getRepositoryInfo(repositoryId,token);
    }
    @PostMapping("/api/repository/getRepositoryList")
    public Result getRepositoryList(@RequestBody Page<Repository> page,@RequestHeader("token")String token){
        return repositoryService.getRepositoryList(page,token);
    }
    @ProjectAuth(checkVisibility = true,checkObject = CheckObjectEnum.CHECK_PROJECT)
    @PostMapping("/api/repository/getRepositoryListByProjectId")
    public Result getRepositoryListByProjectId(@RequestParam Long projectId,@RequestBody Page<Repository> page,@RequestHeader("token")String token){
        return repositoryService.getRepositoryListByProjectId(page,projectId,token);
    }

    @ProjectAuth(checkOperability = true,checkObject = CheckObjectEnum.CHECK_REPOSITORY)
    @PostMapping("/api/repository/uploadRepositoryFile")
    public Result uploadRepositoryFile(@RequestParam Long repositoryId,@RequestParam String path,@RequestParam("file") MultipartFile file){
        return repositoryService.uploadRepositoryFile(repositoryId,path,file);
    }

    @GetMapping("/api/repository/getRepositoryFolder")
    @ProjectAuth(checkVisibility = true,checkObject = CheckObjectEnum.CHECK_REPOSITORY)
    public Result getRepositoryFolder(@RequestParam Long repositoryId,@RequestParam String path){
        return repositoryService.getRepositoryFolder(repositoryId,path);
    }

    @GetMapping("/api/repository/getRepositoryFile")
    @ProjectAuth(checkVisibility = true,checkObject = CheckObjectEnum.CHECK_REPOSITORY)
    public Result getRepositoryFile(@RequestParam Long repositoryId,@RequestParam String path) {
        return repositoryService.getRepositoryFile(repositoryId,path);
    }

    @GetMapping("/api/repository/getRepositoryREADME")
    @ProjectAuth(checkVisibility = true,checkObject = CheckObjectEnum.CHECK_REPOSITORY)
    public Result getRepositoryREADME(@RequestParam Long repositoryId){
        return repositoryService.getRepositoryREADME(repositoryId);
    }

    @UserAuth
    @GetMapping("/api/repository/starRepository")
    @ProjectAuth(checkVisibility = true,checkObject = CheckObjectEnum.CHECK_REPOSITORY)
    public Result starRepository(@RequestParam Long repositoryId,@RequestHeader("token")String token){
        return repositoryService.starRepository(repositoryId,token);
    }

    @UserAuth
    @GetMapping("/api/repository/downloadRepository")
    @ProjectAuth(checkVisibility = true,checkObject = CheckObjectEnum.CHECK_REPOSITORY)
    public Result downloadRepository(@RequestParam Long repositoryId){
        return repositoryService.downloadRepository(repositoryId);
    }
}
