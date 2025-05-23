package com.hx.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hx.common.Result;
import com.hx.entity.PO.*;

/**
 * @author dhx
 * @date 2025/5/16 15:22
 */
public interface AdminService {
    public Result setAdmin(Long userId);

    public Result banUser(Long userId);

    public Result updateUser(User user);

    public Result delComment(Long commentId);

    public Result delPost(Long postId);

    public Result delRepository(Long repositoryId);

    public Result delProject(Long projectId);

    public Result delRelease(Long releaseId);

    public Result getPostList(Page<Post> page);

    public Result getProjectList(Page<Project> page);

    public Result getRepositoryList(Page<Repository> page);

    public Result getReleaseList(Page<Release> page);

    public Result getUserList(Page<User> page);
}
