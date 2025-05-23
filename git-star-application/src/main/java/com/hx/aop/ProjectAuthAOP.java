package com.hx.aop;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hx.aop.annotation.ProjectAuth;
import com.hx.common.Result;
import com.hx.common.enums.AppHttpCodeEnum;
import com.hx.entity.PO.relationship.RepositoryTask;
import com.hx.enums.CheckObjectEnum;
import com.hx.entity.DTO.JoinRequest.AcceptJoinRequestDTO;
import com.hx.entity.DTO.JoinRequest.AddJoinRequestDTO;
import com.hx.entity.DTO.JoinRequest.RefuseJoinRequestDTO;
import com.hx.entity.DTO.Release.DelReleaseDTO;
import com.hx.entity.DTO.Release.NewReleaseDTO;
import com.hx.entity.DTO.Release.UpdateReleaseDTO;
import com.hx.entity.DTO.project.DelProjectDTO;
import com.hx.entity.DTO.project.UpdateProjectDTO;

import com.hx.entity.DTO.repository.DelRepositoryDTO;
import com.hx.entity.DTO.repository.NewRepositoryDTO;
import com.hx.entity.DTO.repository.UpdateRepositoryDTO;
import com.hx.entity.DTO.tag.ProjectTagDTO;
import com.hx.entity.DTO.task.CloseTask;
import com.hx.entity.DTO.task.NewTask;
import com.hx.entity.PO.Project;
import com.hx.entity.PO.relationship.ProjectRepository;
import com.hx.entity.PO.relationship.UserProject;
import com.hx.exception.SystemException;
import com.hx.mapper.ProjectMapper;
import com.hx.mapper.relationship.ProjectRepositoryMapper;
import com.hx.mapper.relationship.RepositoryTaskMapper;
import com.hx.mapper.relationship.UserProjectMapper;
import com.hx.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

/**
 * @author dhx
 * @date 2025/1/9 1:13
 */
@Component
@Order(2)
@Aspect
public class ProjectAuthAOP {
    @Autowired
    private UserProjectMapper userProjectMapper;
    @Autowired
    private ProjectRepositoryMapper projectRepositoryMapper;
    @Autowired
    private RepositoryTaskMapper repositoryTaskMapper;
    @Autowired
    private ProjectMapper projectMapper;
    @Pointcut("@annotation(com.hx.aop.annotation.ProjectAuth)")
    public void projectPointCut(){

    }
    @Around("projectPointCut()")
    public Object projectAuthHandler(ProceedingJoinPoint point) throws Throwable {
        Class<?> aClass = point.getTarget().getClass();
        MethodSignature ms = (MethodSignature)point.getSignature();
        Method method = aClass.getDeclaredMethod(ms.getName(),ms.getParameterTypes());
        ProjectAuth annotation = method.getAnnotation(ProjectAuth.class);
        boolean operability = annotation.checkOperability();
        boolean visibility = annotation.checkVisibility();
        CheckObjectEnum checkObjectEnum = annotation.checkObject();
        Object[] args = point.getArgs();
        Long projectId = getProjectIdFromArgs(args,checkObjectEnum);
        if (projectId == null) {
            return Result.errorResult(AppHttpCodeEnum.PARAM_ERROR);
        }
        Project project = projectMapper.selectById(projectId);
        Boolean visible = project.getVisibility();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String token = request.getHeader("token");

        List<UserProject> list = userProjectMapper.selectList(new QueryWrapper<UserProject>().eq("project_id",projectId));

        Optional<UserProject> optional = null;
        if(visibility&&!visible){
            if(!JwtUtil.validateToken(token)){
                return Result.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            }
            optional = getOptional(token,list);
            if((!optional.isPresent())){
                return Result.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH);
            }
        }
        if(operability){
            if(!JwtUtil.validateToken(token)){
                return Result.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            }
            optional = getOptional(token,list);
//            if((!optional.isPresent()||optional.get().getLevel()!= UserProjectAuthEnum.ADMINISTRATOR)){
//                return Result.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH);
//            }
            if((!optional.isPresent())){
                return Result.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH);
            }
        }
        return point.proceed(point.getArgs());
    }

    private Optional<UserProject> getOptional(String token,List<UserProject> list){
        return list.stream().filter(userProject -> userProject.getUserId().equals(JwtUtil.getUserIdFromToken(token)))
                .findFirst();
    }

    private Long getProjectIdFromArgs(Object[] args,CheckObjectEnum checkObjectEnum) {
        for (Object arg : args) {
            if (arg instanceof DelProjectDTO) {
                return ((DelProjectDTO) arg).getProjectId();
            } else if (arg instanceof UpdateProjectDTO) {
                return ((UpdateProjectDTO) arg).getId();
            } else if(arg instanceof Long) {
                if(checkObjectEnum == CheckObjectEnum.CHECK_REPOSITORY)return getProjectIdByRepositoryId((Long) arg);
                if(checkObjectEnum == CheckObjectEnum.CHECK_TASK)return getProjectIdByTaskId((Long) arg);
                return (Long) args[0];
            } else if(arg instanceof NewRepositoryDTO){
                return ((NewRepositoryDTO) arg).getProjectId();
            } else if(arg instanceof DelRepositoryDTO){
                return getProjectIdByRepositoryId(((DelRepositoryDTO) arg).getRepositoryId());
            } else if(arg instanceof UpdateRepositoryDTO){
                return getProjectIdByRepositoryId(((UpdateRepositoryDTO) arg).getRepositoryId());
            } else if(arg instanceof NewReleaseDTO){
                return ((NewReleaseDTO)arg).getProjectId();
            } else if(arg instanceof DelReleaseDTO){
                return ((DelReleaseDTO)arg).getReleaseId();
            } else if(arg instanceof UpdateReleaseDTO){
                return ((UpdateReleaseDTO)arg).getReleaseId();
            } else if(arg instanceof AddJoinRequestDTO){
                return ((AddJoinRequestDTO)arg).getProjectId();
            } else if(arg instanceof AcceptJoinRequestDTO){
                return ((AcceptJoinRequestDTO)arg).getProjectId();
            } else if(arg instanceof RefuseJoinRequestDTO){
                return ((RefuseJoinRequestDTO)arg).getProjectId();
            } else if(arg instanceof ProjectTagDTO){
                return ((ProjectTagDTO) arg).getProjectId();
            } else if(arg instanceof CloseTask){
                return getProjectIdByRepositoryId(((CloseTask) arg).getRepositoryId());
            } else if(arg instanceof NewTask){
                return getProjectIdByRepositoryId(((NewTask) arg).getRepositoryId());
            }
        }
        return null;
    }
    private Long getProjectIdByRepositoryId(Long repositoryId){
        ProjectRepository projectRepository = projectRepositoryMapper.selectOne(new QueryWrapper<ProjectRepository>().eq("repository_id",repositoryId));
        if(projectRepository==null){
            throw new SystemException(AppHttpCodeEnum.PARAM_ERROR);
        }
        return projectRepository.getProjectId();
    }
    private Long getProjectIdByTaskId(Long taskId){
        RepositoryTask repositoryTask = repositoryTaskMapper.selectOne(new QueryWrapper<RepositoryTask>().eq("task_id",taskId));
        if(repositoryTask==null){
            throw new SystemException(AppHttpCodeEnum.PARAM_ERROR);
        }
        return getProjectIdByRepositoryId(repositoryTask.getRepositoryId());
    }

}
