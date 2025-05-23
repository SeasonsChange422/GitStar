package com.hx.controller;

import com.hx.aop.annotation.ProjectAuth;
import com.hx.aop.annotation.UserAuth;
import com.hx.common.Result;
import com.hx.enums.CheckObjectEnum;
import com.hx.entity.DTO.Release.DelReleaseDTO;
import com.hx.entity.DTO.Release.NewReleaseDTO;
import com.hx.entity.DTO.Release.UpdateReleaseDTO;
import com.hx.service.ReleaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author dhx
 * @date 2025/5/9 22:11
 */
@RestController
public class ReleaseController {
    @Autowired
    private ReleaseService releaseService;

    @ProjectAuth(checkOperability = true,checkObject = CheckObjectEnum.CHECK_PROJECT)
    @UserAuth
    @PostMapping("/api/release/newRelease")
    public Result newRelease(@RequestBody NewReleaseDTO newReleaseDTO){
        return releaseService.newRelease(newReleaseDTO);
    }

    @ProjectAuth(checkOperability = true,checkObject = CheckObjectEnum.CHECK_PROJECT)
    @UserAuth
    @PostMapping("/api/release/delRelease")
    public Result delRelease(@RequestBody DelReleaseDTO delReleaseDTO){
        return releaseService.delRelease(delReleaseDTO);
    }

    @ProjectAuth(checkOperability = true,checkObject = CheckObjectEnum.CHECK_PROJECT)
    @UserAuth
    @PostMapping("/api/release/updateRelease")
    public Result updateRelease(@RequestBody UpdateReleaseDTO updateReleaseDTO){
        return releaseService.updateRelease(updateReleaseDTO);
    }

    @ProjectAuth(checkVisibility = true,checkObject = CheckObjectEnum.CHECK_PROJECT)
    @UserAuth
    @GetMapping("/api/release/getReleaseOfProject")
    public Result getReleaseOfProject(@RequestParam Long projectId){
        return releaseService.getReleaseOfProject(projectId);
    }

//    @ProjectAuth(checkVisibility = true,checkObject = CheckObjectEnum.CHECK_PROJECT)
    @UserAuth
    @GetMapping("/api/release/getReleaseInfo")
    public Result getReleaseInfo(@RequestParam Long releaseId){
        return releaseService.getReleaseInfo(releaseId);
    }
}
