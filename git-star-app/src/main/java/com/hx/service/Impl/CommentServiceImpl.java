package com.hx.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hx.common.Result;
import com.hx.common.enums.AppHttpCodeEnum;
import com.hx.entity.PO.Comment;
import com.hx.entity.PO.User;
import com.hx.entity.PO.relationship.PostComment;
import com.hx.entity.PO.relationship.UserComment;
import com.hx.mapper.CommentMapper;
import com.hx.mapper.UserMapper;
import com.hx.mapper.relationship.PostCommentMapper;
import com.hx.mapper.relationship.UserCommentMapper;
import com.hx.service.CommentService;
import com.hx.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dhx
 * @date 2025/1/8 16:34
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserCommentMapper userCommentMapper;
    @Autowired
    private PostCommentMapper postCommentMapper;

    @Override
    public Result newComment(Comment comment,String token) {
        Long userId = JwtUtil.getUserIdFromToken(token);
        if(commentMapper.insert(comment)==1&&userCommentMapper.insert(new UserComment(userId,comment.getId()))==1){
            return Result.okResult(200,"发表成功");
        }
        return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @Override
    public Result deleteComment(Comment comment) {
        if(commentMapper.deleteById(comment)==1){
            return Result.okResult(200,"删除成功");
        }
        return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @Override
    public Result getRootCommentList(Long postId,Page<Comment> page) {
        List<PostComment> list = postCommentMapper.selectList(new QueryWrapper<PostComment>().eq("post_id",postId));
        List<Long> commentIds = list.stream()
                .map(PostComment::getCommentId)
                .collect(Collectors.toList());
        if (commentIds.isEmpty()) {
            return Result.okResult(new ArrayList<>());
        }
        Page<Comment> comments = commentMapper.selectPage(page,new QueryWrapper<Comment>()
                .in("comment_id", commentIds)
                .eq("comment_rootId", -1));
        return Result.okResult(comments);
    }

    @Override
    public Result getChildCommentList(Long rootId, Page<Comment> page) {
        List<Comment> list = commentMapper.selectList(new QueryWrapper<Comment>().eq("comment_rootId",rootId));
        return Result.okResult(list);
    }
}
