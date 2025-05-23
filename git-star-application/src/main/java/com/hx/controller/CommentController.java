package com.hx.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hx.aop.annotation.UserAuth;
import com.hx.common.Result;
import com.hx.entity.DTO.comment.DelCommentDTO;
import com.hx.entity.DTO.comment.NewCommentDTO;
import com.hx.entity.PO.Comment;
import com.hx.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author dhx
 * @date 2025/1/8 16:24
 */
@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;

    @UserAuth
    @PostMapping("/api/comment/newComment")
    public Result newComment(@RequestBody NewCommentDTO comment,@RequestParam Long postId, @RequestHeader("token")String token){
        return commentService.newComment(comment,postId,token);
    }
    @UserAuth
    @PostMapping("/api/comment/deleteComment")
    public Result deleteComment(@RequestBody DelCommentDTO comment){
        return commentService.deleteComment(comment);
    }

    @PostMapping("/api/comment/getRootCommentList")
    public Result getRootCommentList(@RequestBody Page<Comment> page,@RequestParam Long postId){
        return commentService.getRootCommentList(postId,page);
    }

    @PostMapping("/api/comment/getChildCommentList")
    public Result getChildCommentList(@RequestBody Page<Comment> page,@RequestParam Long commentId){
        return commentService.getChildCommentList(commentId,page);
    }

    @GetMapping("/api/comment/getCommentInfo")
    public Result getCommentInfo(@RequestParam Long commentId){
        return commentService.getCommentInfo(commentId);
    }
}
