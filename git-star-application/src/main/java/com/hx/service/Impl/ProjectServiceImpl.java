package com.hx.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hx.common.Result;
import com.hx.common.enums.AppHttpCodeEnum;
import com.hx.enums.UserProjectAuthEnum;
import com.hx.entity.DTO.post.PostDTO;
import com.hx.entity.DTO.project.NewProjectDTO;
import com.hx.entity.DTO.project.UpdateProjectDTO;
import com.hx.entity.DTO.user.DeveloperDTO;
import com.hx.entity.PO.Post;
import com.hx.entity.PO.Project;
import com.hx.entity.PO.User;
import com.hx.entity.PO.relationship.PostComment;
import com.hx.entity.PO.relationship.PostProject;
import com.hx.entity.PO.relationship.UserProject;
import com.hx.mapper.PostMapper;
import com.hx.mapper.ProjectMapper;
import com.hx.mapper.UserMapper;
import com.hx.mapper.relationship.PostCommentMapper;
import com.hx.mapper.relationship.PostProjectMapper;
import com.hx.mapper.relationship.UserProjectMapper;
import com.hx.service.ProjectService;
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
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectMapper projectMapper;
    @Autowired
    private UserProjectMapper userProjectMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PostProjectMapper postProjectMapper;
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private PostCommentMapper postCommentMapper;
    @Override
    public Result newProject(NewProjectDTO newProjectDTO,String token) {
        Project project = new Project(newProjectDTO.getName(),newProjectDTO.getVisibility(),newProjectDTO.getLogo(),newProjectDTO.getDescription());
        if(projectMapper.insert(project)==1&&userProjectMapper.insert(new UserProject(JwtUtil.getUserIdFromToken(token),project.getId(), UserProjectAuthEnum.ADMINISTRATOR))==1){
            System.out.println(project);
            return Result.okResult(project);
        }
        return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }
    @Override
    public Result delProject(Long projectId) {
        if(userProjectMapper.delete(new QueryWrapper<UserProject>().eq("project_id",projectId))!=0&&projectMapper.deleteById(projectId)==1){
            return Result.okResult(200,"删除成功");
        }
        return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }
    @Override
    public Result updateProject(UpdateProjectDTO updateProjectDTO) {
        Project project = projectMapper.selectById(updateProjectDTO.getId());
        project.setName(updateProjectDTO.getName());
        project.setVisibility(updateProjectDTO.getVisibility());
        project.setLogo(updateProjectDTO.getLogo());
        project.setDescription(updateProjectDTO.getDescription());
        if(projectMapper.updateById(project)==1){
            return Result.okResult(200,"修改成功");
        }
        return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }
    @Override
    public Result getProjectInfo(Long projectId) {
        return Result.okResult(projectMapper.selectById(projectId));
    }

    @Override
    public Result getProjectList(Page<Project> page) {
        Page<Project> projectPage = projectMapper.selectPage(page,new QueryWrapper<Project>().eq("project_visibility",true).orderByDesc("createTime"));
        return Result.okResult(projectPage);
    }

    @Override
    public Result getProjectListByToken(Page<Project> page,String token) {
        Long userId = JwtUtil.getUserIdFromToken(token);
        List<UserProject> list = userProjectMapper.selectList(new QueryWrapper<UserProject>().eq("user_id",userId));
        List<Long> projectIds = list.stream()
                .map(UserProject::getProjectId)
                .collect(Collectors.toList());
        if (projectIds.isEmpty()) {
            return Result.okResult(new ArrayList<>());
        }
        Page<Project> projects = projectMapper.selectPage(page,new QueryWrapper<Project>()
                .in("project_id", projectIds));
        return Result.okResult(projects);
    }

    @Override
    public Result searchProject(String key) {
        try{
            return Result.okResult(projectMapper.selectList(new QueryWrapper<Project>().like("project_name",key)));
        }catch (Exception e){
            e.printStackTrace();
            return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public Result getUserOfProject(Long projectId) {
        List<UserProject> userProjects = userProjectMapper.selectList(new QueryWrapper<UserProject>().eq("project_id",projectId));
        List<Long> userIds = userProjects.stream().map(UserProject::getUserId).collect(Collectors.toList());
        List<User> users = userMapper.selectBatchIds(userIds);
        Map<Long,UserProject> userProjectsMap = userProjects.stream().collect(Collectors.toMap(UserProject::getUserId, Function.identity()));
        List<DeveloperDTO> developerDTOS = users.stream().map(user -> {
            UserProject userProject= userProjectsMap.get(user.getId());
            return new DeveloperDTO(user,userProject.getLevel());
        }).collect(Collectors.toList());
        return Result.okResult(developerDTOS);
    }

    @Override
    public Result getPostOfProject(Long projectId) {
        List<PostProject>list = postProjectMapper.selectList(new QueryWrapper<PostProject>().eq("project_id",projectId));
        if(list.isEmpty()){
            return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        List<Long>postIds = list.stream().map(PostProject::getPostId).collect(Collectors.toList());
        List<Post> posts = postMapper.selectBatchIds(postIds);
        return Result.okResult(postList2PostDTOlist(posts));
    }

    private List<PostDTO> postList2PostDTOlist(List<Post> list){
        List<Long> postIds = list.stream().map(Post::getId).collect(Collectors.toList());
        List<PostComment> postComments = postCommentMapper.selectList(new QueryWrapper<PostComment>().in("post_id",postIds));
        Map<Long,Integer> map = postComments.stream()
                .collect(Collectors.groupingBy(PostComment::getPostId, Collectors.summingInt(pc -> 1)));
        List<PostDTO> postDTOS = list.stream().map(post -> new PostDTO(post,map.getOrDefault(post.getId(),0))).collect(Collectors.toList());
        return postDTOS;
    }
}
