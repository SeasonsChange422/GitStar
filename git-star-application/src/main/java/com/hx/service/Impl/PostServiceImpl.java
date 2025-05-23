package com.hx.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hx.aop.annotation.UserAuth;
import com.hx.common.Result;
import com.hx.common.enums.AppHttpCodeEnum;
import com.hx.entity.DTO.post.DelPostDTO;
import com.hx.entity.DTO.post.NewPostDTO;
import com.hx.entity.DTO.post.PostDTO;
import com.hx.entity.DTO.post.UpdatePostDTO;
import com.hx.entity.DTO.repository.RepositoryDTO;
import com.hx.entity.PO.Post;
import com.hx.entity.PO.Project;
import com.hx.entity.PO.Repository;
import com.hx.entity.PO.User;
import com.hx.entity.PO.relationship.PostComment;
import com.hx.entity.PO.relationship.PostProject;
import com.hx.entity.PO.relationship.UserPost;
import com.hx.mapper.PostMapper;
import com.hx.mapper.ProjectMapper;
import com.hx.mapper.UserMapper;
import com.hx.mapper.relationship.PostCommentMapper;
import com.hx.mapper.relationship.PostProjectMapper;
import com.hx.mapper.relationship.UserPostMapper;
import com.hx.service.PostService;
import com.hx.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ProjectMapper projectMapper;
    @Autowired
    private PostCommentMapper postCommentMapper;
    @Override
    public Result newPost(NewPostDTO newPostDTO, String token) {
        Long userId = JwtUtil.getUserIdFromToken(token);
        Post post = new Post(newPostDTO.getTitle(),newPostDTO.getImg(), newPostDTO.getContext());
        if(postMapper.insert(post)==1&&
                postProjectMapper.insert(new PostProject(post.getId(), newPostDTO.getProjectId()))==1&&
                userPostMapper.insert(new UserPost(userId,post.getId()))==1){
            return Result.okResult(200,"发布成功");
        }
        return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @Override
    public Result delPost(DelPostDTO delPostDTO) {
        try {
            postMapper.deleteById(delPostDTO.getPostId());
            return Result.okResult(200,"删除成功");
        } catch (Exception e){
            e.printStackTrace();
            return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public Result updatePost(UpdatePostDTO updatePostDTO) {
        if(postMapper.updateById(new Post(updatePostDTO.getTitle(),updatePostDTO.getPostId(), updatePostDTO.getImg(), updatePostDTO.getContext()))==1){
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
        Page<Post> postPage = postMapper.selectPage(page,new QueryWrapper<Post>().orderByDesc("createTime"));
        return Result.okResult(postPage);
    }

    @Override
    public Result getPostListByToken(Page<Post> page,String token) {
        try{
            Long userId = JwtUtil.getUserIdFromToken(token);
            List<UserPost> list = userPostMapper.selectList(new QueryWrapper<UserPost>().eq("user_id",userId));
            List<Long> postIds = list.stream()
                    .map(UserPost::getPostId)
                    .collect(Collectors.toList());
            if (postIds.isEmpty()) {
                return Result.okResult(new ArrayList<>());
            }
            List<Post> posts = postMapper.selectList(new QueryWrapper<Post>()
                    .in("post_id", postIds));
            return Result.okResult(posts);
        } catch (Exception e){
            e.printStackTrace();
            return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public Result getAuthor(Long postId) {
        try{
            UserPost userPost = userPostMapper.selectOne(new QueryWrapper<UserPost>().eq("post_id",postId));
            User user = userMapper.selectById(userPost.getUserId());
            return Result.okResult(user);
        } catch (Exception e){
            e.printStackTrace();
            return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public Result getProjectOfPost(Long postId) {
        PostProject postProject = postProjectMapper.selectOne(new QueryWrapper<PostProject>().eq("post_id",postId));
        Project project = projectMapper.selectById(postProject.getProjectId());
        return Result.okResult(project);
    }

    @Override
    public Result searchPost(String key) {
        try{
            return Result.okResult(postMapper.selectList(new QueryWrapper<Post>().like("post_title",key).or().like("post_context",key)));
        }catch (Exception e){
            e.printStackTrace();
            return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    private Page<PostDTO> getPostDTOPage(Page<Post> postPage) {
        List<Post> posts = postPage.getRecords();


        List<PostDTO> dtoList = postList2PostDTOlist(posts);
        Page<PostDTO> postDTOPage = new Page<>(
                postPage.getCurrent(),
                postPage.getSize(),
                postPage.getTotal()
        );
        postDTOPage.setRecords(dtoList);
        postDTOPage.setPages(postPage.getPages());
        return postDTOPage;
    }

    private List<PostDTO> postList2PostDTOlist(List<Post> list){
        List<Long> postIds = list.stream().map(Post::getId).collect(Collectors.toList());
        List<PostComment> postComments = postCommentMapper.selectList(new QueryWrapper<PostComment>().in("post_id",postIds));
        Map<Long,Integer> map = postComments.stream()
                .collect(Collectors.groupingBy(PostComment::getPostId, Collectors.summingInt(pc -> 1)));
        List<PostDTO> postDTOS = list.stream().map(post -> {
            return new PostDTO(post,map.get(post.getId()));
        }).collect(Collectors.toList());
        return postDTOS;
    }
}
