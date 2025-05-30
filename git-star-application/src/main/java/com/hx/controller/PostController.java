package com.hx.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hx.aop.annotation.PostAuth;
import com.hx.aop.annotation.UserAuth;
import com.hx.common.Result;
import com.hx.entity.DTO.post.DelPostDTO;
import com.hx.entity.DTO.post.NewPostDTO;
import com.hx.entity.DTO.post.UpdatePostDTO;
import com.hx.entity.PO.Post;
import com.hx.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author dhx
 * @date 2025/1/8 16:25
 */
@RestController
public class PostController {
    @Autowired
    private PostService postService;

    @UserAuth
    @PostMapping("/api/post/newPost")
    public Result newPost(@RequestBody NewPostDTO newPostDTO, @RequestHeader("token")String token){
        return postService.newPost(newPostDTO,token);
    }

    @UserAuth
    @PostAuth
    @PostMapping("/api/post/delPost")
    public Result delPost(@RequestBody DelPostDTO delPostDTO){
        return postService.delPost(delPostDTO);
    }

    @UserAuth
    @PostAuth
    @PostMapping("/api/post/updatePost")
    public Result updatePost(@RequestBody UpdatePostDTO updatePostDTO){
        return postService.updatePost(updatePostDTO);
    }

    @GetMapping("/api/post/getPostInfo")
    public Result getPostInfo(@RequestParam Long postId){
        return postService.getPostInfo(postId);
    }

    @PostMapping("/api/post/getPostList")
    public Result getPostList(@RequestBody Page<Post> page){
        return postService.getPostList(page);
    }

    @UserAuth
    @PostMapping("/api/post/getPostListByToken")
    public Result getPostListByToken(@RequestBody Page<Post> page,@RequestHeader("token")String token){
        return postService.getPostListByToken(page,token);
    }

    @GetMapping("/api/post/getAuthor")
    public Result getAuthor(@RequestParam Long postId){
        return postService.getAuthor(postId);
    }

    @GetMapping("/api/post/getProjectOfPost")
    public Result getProjectOfPost(@RequestParam Long postId){
        return postService.getProjectOfPost(postId);
    }

    @GetMapping("/api/post/searchPost")
    public Result searchPost(@RequestParam String key) {
        return postService.searchPost(key);
    }
}
