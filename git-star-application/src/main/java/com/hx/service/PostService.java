package com.hx.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hx.common.Result;
import com.hx.entity.DTO.post.DelPostDTO;
import com.hx.entity.DTO.post.NewPostDTO;
import com.hx.entity.DTO.post.UpdatePostDTO;
import com.hx.entity.PO.Post;

/**
 * @author dhx
 * @date 2025/1/8 16:32
 */
public interface PostService {
    public Result newPost(NewPostDTO newPostDTO,String token);
    public Result delPost(DelPostDTO delPostDTO);
    public Result updatePost(UpdatePostDTO updatePostDTO);
    public Result getPostInfo(Long postId);
    public Result getPostList(Page<Post> page);
    public Result getPostListByToken(Page<Post> page,String token);
    public Result getAuthor(Long postId);
    public Result getProjectOfPost(Long postId);
    public Result searchPost(String key);
}
