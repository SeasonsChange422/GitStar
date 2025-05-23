package com.hx.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hx.common.Result;
import com.hx.common.enums.AppHttpCodeEnum;
import com.hx.entity.DTO.comment.CommentDTO;
import com.hx.entity.DTO.comment.DelCommentDTO;
import com.hx.entity.DTO.comment.NewCommentDTO;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
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
    public Result newComment(NewCommentDTO newCommentDTO,Long postId, String token) {
        Long userId = JwtUtil.getUserIdFromToken(token);
        Comment comment = new Comment(newCommentDTO.getRootId(),newCommentDTO.getText());
        if(commentMapper.insert(comment)==1
                &&userCommentMapper.insert(new UserComment(userId,comment.getId()))==1
                &&postCommentMapper.insert(new PostComment(postId, comment.getId()))==1){
            return Result.okResult(200,"发表成功");
        }
        return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @Override
    public Result deleteComment(DelCommentDTO delCommentDTO) {
        Comment comment = new Comment(delCommentDTO.getCommentId());
        if(commentMapper.deleteById(comment)==1){
            return Result.okResult(200,"删除成功");
        }
        return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @Override
    public Result getRootCommentList(Long postId, Page<Comment> page) {
        List<PostComment> postComments = postCommentMapper.selectList(
                new QueryWrapper<PostComment>().eq("post_id", postId)
        );
        List<Long> commentIds = postComments.stream()
                .map(PostComment::getCommentId)
                .collect(Collectors.toList());
        if (commentIds.isEmpty()) {
            return Result.okResult(new ArrayList<>());
        }
        List<UserComment> userComments = userCommentMapper.selectList(
                new QueryWrapper<UserComment>().in("comment_id", commentIds)
        );
        List<Long> userIds = userComments.stream()
                .map(UserComment::getUserId)
                .collect(Collectors.toList());

        Map<Long, User> userMap = new HashMap<>();
        if (!userIds.isEmpty()) {
            List<User> users = userMapper.selectBatchIds(userIds);
            userMap = users.stream()
                    .collect(Collectors.toMap(User::getId, Function.identity()));
        }

        Page<Comment> commentPage = commentMapper.selectPage(page, new QueryWrapper<Comment>()
                .in("comment_id", commentIds)
                .eq("comment_rootId", -1));
        Map<Long, User> finalUserMap = userMap;
        List<CommentDTO> commentDTOList = commentPage.getRecords().stream().map(comment -> {
            CommentDTO dto = new CommentDTO();
            dto.setCommentId(comment.getId());
            dto.setContent(comment.getText());
            dto.setCommentRootId(comment.getRootId());
            userComments.stream()
                    .filter(uc -> uc.getCommentId().equals(comment.getId()))
                    .findFirst()
                    .ifPresent(uc -> {
                        User user = finalUserMap.get(uc.getUserId());
                        if (user != null) {
                            dto.setUserId(user.getId());
                            dto.setUserNickName(user.getNickname());
                            dto.setUserAvatar(user.getAvatar());
                        }
                    });
            return dto;
        }).collect(Collectors.toList());

        Page<CommentDTO> resultPage = new Page<>(
                commentPage.getCurrent(),
                commentPage.getSize(),
                commentPage.getTotal()
        );
        resultPage.setRecords(commentDTOList);

        return Result.okResult(resultPage);
    }

    @Override
    public Result getChildCommentList(Long rootId, Page<Comment> page) {
        List<Comment> list = commentMapper.selectList(new QueryWrapper<Comment>().eq("comment_rootId",rootId));
        return Result.okResult(list);
    }

    @Override
    public Result getCommentInfo(Long commentId) {
        try{
            Comment comment = commentMapper.selectById(commentId);
            UserComment userComment = userCommentMapper.selectOne(new QueryWrapper<UserComment>().eq("comment_id",commentId));
            User user = userMapper.selectById(userComment.getUserId());
            CommentDTO commentDTO = new CommentDTO(commentId,comment.getText(),comment.getRootId(), user.getId(), user.getNickname(),user.getAvatar());
            return Result.okResult(commentDTO);
        } catch (Exception e){
            e.printStackTrace();
            return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }
}
