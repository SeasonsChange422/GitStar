package com.hx.controller;

import com.hx.aop.annotation.ProjectAuth;
import com.hx.aop.annotation.UserAuth;
import com.hx.common.Result;
import com.hx.enums.CheckObjectEnum;
import com.hx.entity.DTO.tag.ProjectTagDTO;
import com.hx.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dhx
 * @date 2025/5/10 11:03
 */
@RestController
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/api/tag/getTags")
    public Result getTags(){
        return tagService.getTags();
    }

    @ProjectAuth(checkVisibility = true,checkObject = CheckObjectEnum.CHECK_PROJECT)
    @GetMapping("/api/tag/getTagOfProject")
    public Result getTagOfProject(Long projectId){
        return tagService.getTagOfProject(projectId);
    }

    @UserAuth
    @ProjectAuth(checkOperability = true,checkObject = CheckObjectEnum.CHECK_PROJECT)
    @PostMapping("/api/tag/addTagToProject")
    public Result addTagToProject(@RequestBody ProjectTagDTO projectTagDTO){
        return tagService.addTagToProject(projectTagDTO);
    }

    @UserAuth
    @ProjectAuth(checkOperability = true,checkObject = CheckObjectEnum.CHECK_PROJECT)
    @PostMapping("/api/tag/removeTagFromProject")
    public Result removeTagFromProject(@RequestBody ProjectTagDTO projectTagDTO){
        return tagService.removeTagFromProject(projectTagDTO);
    }
}
