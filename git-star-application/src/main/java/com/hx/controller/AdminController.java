package com.hx.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hx.aop.annotation.AdminAuth;
import com.hx.common.Result;
import com.hx.entity.DTO.notice.DelNoticeDTO;
import com.hx.entity.DTO.notice.NewNoticeDTO;
import com.hx.entity.DTO.tag.DelTagDTO;
import com.hx.entity.DTO.tag.NewTagDTO;
import com.hx.entity.PO.*;
import com.hx.mapper.NoticeMapper;
import com.hx.service.AdminService;
import com.hx.service.NoticeService;
import com.hx.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author dhx
 * @date 2025/2/28 10:22
 */
@RestController
public class AdminController {
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private TagService tagService;
    @Autowired
    private AdminService adminService;

    @AdminAuth
    @PostMapping("/api/admin/newNotice")
    public Result newNotice(@RequestBody NewNoticeDTO newNoticeDTO){
        return noticeService.newNotice(newNoticeDTO);
    }

    @AdminAuth
    @PostMapping("/api/admin/delNotice")
    public Result delNotice(@RequestBody DelNoticeDTO delNoticeDTO){
        return noticeService.delNotice(delNoticeDTO);
    }

    @AdminAuth
    @PostMapping("/api/admin/newTag")
    public Result newTag(@RequestBody NewTagDTO newTagDTO){
        return tagService.newTag(newTagDTO);
    }

    @AdminAuth
    @PostMapping("/api/admin/delTag")
    public Result delTag(@RequestBody DelTagDTO delTagDTO){
        return tagService.delTag(delTagDTO);
    }

    @AdminAuth
    @PostMapping("/api/admin/setAdmin")
    public Result setAdmin(@RequestParam Long userId){
        return adminService.setAdmin(userId);
    }

    @AdminAuth
    @PostMapping("/api/admin/banUser")
    public Result banUser(@RequestParam Long userId){
        return adminService.banUser(userId);
    }

    @AdminAuth
    @PostMapping("/api/admin/updateUser")
    public Result updateUser(@RequestBody User user){
        return adminService.updateUser(user);
    }

    @AdminAuth
    @PostMapping("/api/admin/delComment")
    public Result delComment(@RequestParam Long commentId){
        return adminService.delComment(commentId);
    }

    @AdminAuth
    @PostMapping("/api/admin/delPost")
    public Result delPost(@RequestParam Long postId){
        return adminService.delPost(postId);
    }

    @AdminAuth
    @PostMapping("/api/admin/delRepository")
    public Result delRepository(@RequestParam Long repositoryId){
        return adminService.delRepository(repositoryId);
    }

    @AdminAuth
    @PostMapping("/api/admin/delProject")
    public Result delProject(@RequestParam Long projectId){
        return adminService.delProject(projectId);
    }

    @AdminAuth
    @PostMapping("/api/admin/delRelease")
    public Result delRelease(@RequestParam Long releaseId){
        return adminService.delRelease(releaseId);
    }

    @AdminAuth
    @PostMapping("/api/admin/getReleaseList")
    public Result getReleaseList(@RequestBody Page<Release> page){
        return adminService.getReleaseList(page);
    }
    @AdminAuth
    @PostMapping("/api/admin/getPostList")
    public Result getPostList(@RequestBody Page<Post> page){
        return adminService.getPostList(page);
    }
    @AdminAuth
    @PostMapping("/api/admin/getProjectList")
    public Result getProjectList(@RequestBody Page<Project> page){
        return adminService.getProjectList(page);
    }
    @AdminAuth
    @PostMapping("/api/admin/getRepositoryList")
    public Result getRepositoryList(@RequestBody Page<Repository> page){
        return adminService.getRepositoryList(page);
    }
    @AdminAuth
    @PostMapping("/api/admin/getUserList")
    public Result getUserList(@RequestBody Page<User> page){
        return adminService.getUserList(page);
    }
}
