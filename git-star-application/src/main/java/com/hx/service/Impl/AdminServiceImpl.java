package com.hx.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hx.common.Result;
import com.hx.common.enums.AppHttpCodeEnum;
import com.hx.entity.PO.*;
import com.hx.entity.PO.relationship.PostComment;
import com.hx.entity.PO.relationship.UserComment;
import com.hx.mapper.*;
import com.hx.mapper.relationship.PostCommentMapper;
import com.hx.mapper.relationship.UserCommentMapper;
import com.hx.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dhx
 * @date 2025/5/16 15:22
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserCommentMapper userCommentMapper;
    @Autowired
    private PostCommentMapper postCommentMapper;
    @Autowired
    private FeedbackMapper feedbackMapper;
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private RepositoryMapper repositoryMapper;
    @Autowired
    private ProjectMapper projectMapper;
    @Autowired
    private ReleaseMapper releaseMapper;
    @Override
    public Result setAdmin(Long userId) {
        try {
            User user = userMapper.selectById(userId);
            user.setIsAdmin(true);
            userMapper.updateById(user);
            return Result.okResult();
        } catch (Exception e){
            e.printStackTrace();
            return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public Result banUser(Long userId) {
        try {
            User user = userMapper.selectById(userId);
            user.setIsBan(!user.getIsBan());
            userMapper.updateById(user);
            return Result.okResult();
        } catch (Exception e){
            e.printStackTrace();
            return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public Result updateUser(User user) {
        try {
            userMapper.updateById(user);
            return Result.okResult();
        } catch (Exception e){
            e.printStackTrace();
            return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public Result delComment(Long commentId) {
        try {
            List<Feedback> feedbacks = feedbackMapper.selectList(new QueryWrapper<Feedback>().eq("object_id",commentId));
            for (Feedback f :feedbacks) {
                f.setSolved(true);
                feedbackMapper.updateById(f);
            }
            commentMapper.deleteById(commentId);
            return Result.okResult();
        } catch (Exception e){
            e.printStackTrace();
            return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public Result delPost(Long postId) {
        try {
            List<Feedback> feedbacks = feedbackMapper.selectList(new QueryWrapper<Feedback>().eq("object_id",postId));
            for (Feedback f :feedbacks) {
                f.setSolved(true);
                feedbackMapper.updateById(f);
            }
            postMapper.deleteById(postId);
            return Result.okResult();
        } catch (Exception e){
            e.printStackTrace();
            return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public Result delRepository(Long repositoryId) {
        try {
            List<Feedback> feedbacks = feedbackMapper.selectList(new QueryWrapper<Feedback>().eq("object_id",repositoryId));
            for (Feedback f :feedbacks) {
                f.setSolved(true);
                feedbackMapper.updateById(f);
            }
            repositoryMapper.deleteById(repositoryId);
            return Result.okResult();
        } catch (Exception e){
            e.printStackTrace();
            return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public Result delProject(Long projectId) {
        try {
            List<Feedback> feedbacks = feedbackMapper.selectList(new QueryWrapper<Feedback>().eq("object_id",projectId));
            for (Feedback f :feedbacks) {
                f.setSolved(true);
                feedbackMapper.updateById(f);
            }
            projectMapper.deleteById(projectId);
            return Result.okResult();
        } catch (Exception e){
            e.printStackTrace();
            return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public Result delRelease(Long releaseId) {
        try {
            List<Feedback> feedbacks = feedbackMapper.selectList(new QueryWrapper<Feedback>().eq("object_id",releaseId));
            for (Feedback f :feedbacks) {
                f.setSolved(true);
                feedbackMapper.updateById(f);
            }
            releaseMapper.deleteById(releaseId);
            return Result.okResult();
        } catch (Exception e){
            e.printStackTrace();
            return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public Result getPostList(Page<Post> page) {
        try {
            return Result.okResult(postMapper.selectPage(page,new QueryWrapper<Post>()));
        } catch (Exception e){
            e.printStackTrace();
            return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public Result getProjectList(Page<Project> page) {
        try {
            return Result.okResult(projectMapper.selectPage(page,new QueryWrapper<Project>()));
        } catch (Exception e){
            e.printStackTrace();
            return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public Result getRepositoryList(Page<Repository> page) {
        try {
            return Result.okResult(repositoryMapper.selectPage(page,new QueryWrapper<Repository>()));
        } catch (Exception e){
            e.printStackTrace();
            return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public Result getReleaseList(Page<Release> page) {
        try {
            return Result.okResult(releaseMapper.selectPage(page,new QueryWrapper<Release>()));
        } catch (Exception e){
            e.printStackTrace();
            return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public Result getUserList(Page<User> page) {
        try {
            return Result.okResult(userMapper.selectPage(page,new QueryWrapper<User>()));
        } catch (Exception e){
            e.printStackTrace();
            return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }
}
