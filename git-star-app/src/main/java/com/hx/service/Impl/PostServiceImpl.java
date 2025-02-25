package com.hx.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hx.common.Result;
import com.hx.common.enums.AppHttpCodeEnum;
import com.hx.entity.DTO.post.DelPostDTO;
import com.hx.entity.DTO.post.NewPostDTO;
import com.hx.entity.DTO.post.UpdatePostDTO;
import com.hx.entity.PO.Post;
import com.hx.entity.PO.relationship.PostProject;
import com.hx.entity.PO.relationship.UserPost;
import com.hx.mapper.PostMapper;
import com.hx.mapper.relationship.PostProjectMapper;
import com.hx.mapper.relationship.UserPostMapper;
import com.hx.service.PostService;
import com.hx.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dhx
 * @date 2025/1/8 16:33
 */
@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private PostProjectMapper postProjectMapper;
    @Autowired
    private UserPostMapper userPostMapper;
    @Override
    public Result newPost(NewPostDTO newPostDTO, String token) {
        Long userId = JwtUtil.getUserIdFromToken(token);
        Post post = new Post(newPostDTO.getImg(), newPostDTO.getContext());
        if(postMapper.insert(post)==1&&
                postProjectMapper.insert(new PostProject(post.getId(), newPostDTO.getProjectId()))==1&&
                userPostMapper.insert(new UserPost(userId,post.getId()))==1){
            return Result.okResult(200,"发布成功");
        }
        return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @Override
    public Result delPost(DelPostDTO delPostDTO) {
        QueryWrapper<UserPost> userPostQueryWrapper = new QueryWrapper<UserPost>().eq("post_id",delPostDTO.getPostId());
        QueryWrapper<PostProject> postProjectQueryWrapper = new QueryWrapper<PostProject>().eq("post_id",delPostDTO.getPostId());
        if(userPostMapper.delete(userPostQueryWrapper)==1&&
                postProjectMapper.delete(postProjectQueryWrapper)==1&&
                postMapper.deleteById(delPostDTO.getPostId())==1){
            return Result.okResult(200,"删除成功");
        }
        return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @Override
    public Result updatePost(UpdatePostDTO updatePostDTO) {
        if(postMapper.updateById(new Post(updatePostDTO.getPostId(), updatePostDTO.getImg(), updatePostDTO.getContext()))==1){
            return Result.okResult(200,"修改成功");
        }
        return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @Override
    public Result getPostInfo(Long postId) {
        Post post = postMapper.selectById(postId);
        if(post!=null){
            return Result.okResult(post);
        }
        return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @Override
    public Result getPostList(Page<Post> page) {
        Page<Post> postPage = postMapper.selectPage(page,new QueryWrapper<Post>());
        return Result.okResult(postPage);
    }

    @Override
    public Result getPostListByToken(Page<Post> page,String token) {
        Long userId = JwtUtil.getUserIdFromToken(token);
        Page<Post> postPage = postMapper.selectPage(page,new QueryWrapper<Post>().eq("user_id",userId));
        return Result.okResult(postPage);
    }
}
