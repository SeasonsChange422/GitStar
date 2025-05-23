package com.hx.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hx.common.Result;
import com.hx.entity.DTO.comment.DelCommentDTO;
import com.hx.entity.DTO.comment.NewCommentDTO;
import com.hx.entity.PO.Comment;

/**
 * @author dhx
 * @date 2025/1/8 16:32
 */
public interface CommentService {
    public Result newComment(NewCommentDTO comment,Long postId, String token);

    public Result deleteComment(DelCommentDTO comment);

    public Result getRootCommentList(Long postId, Page<Comment> page);

    public Result getChildCommentList(Long commentId, Page<Comment> page);

    public Result getCommentInfo(Long commentId);
}
