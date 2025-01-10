package com.hx.aop;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hx.aop.annotation.PostAuth;
import com.hx.aop.annotation.ProjectAuth;
import com.hx.common.Result;
import com.hx.common.enums.AppHttpCodeEnum;
import com.hx.common.enums.CheckObjectEnum;
import com.hx.common.enums.UserProjectAuthEnum;
import com.hx.entity.DTO.post.DelPostDTO;
import com.hx.entity.DTO.post.UpdatePostDTO;
import com.hx.entity.DTO.project.DelProjectDTO;
import com.hx.entity.DTO.project.UpdateProjectDTO;
import com.hx.entity.DTO.repository.DelRepositoryDTO;
import com.hx.entity.DTO.repository.NewRepositoryDTO;
import com.hx.entity.DTO.repository.UpdateRepositoryDTO;
import com.hx.entity.PO.Project;
import com.hx.entity.PO.relationship.ProjectRepository;
import com.hx.entity.PO.relationship.UserPost;
import com.hx.entity.PO.relationship.UserProject;
import com.hx.exception.SystemException;
import com.hx.mapper.relationship.UserPostMapper;
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
 * @date 2025/1/10 17:41
 */
@Component
@Order(2)
@Aspect
public class PostAuthAOP {
    @Autowired
    private UserPostMapper userPostMapper;
    @Pointcut("@annotation(com.hx.aop.annotation.PostAuth)")
    public void postPointCut(){

    }
    @Around("postPointCut()")
    public Object postAuthHandler(ProceedingJoinPoint point) throws Throwable {
        Class<?> aClass = point.getTarget().getClass();
        MethodSignature ms = (MethodSignature)point.getSignature();
        Method method = aClass.getDeclaredMethod(ms.getName(),ms.getParameterTypes());
        PostAuth annotation = method.getAnnotation(PostAuth.class);
        boolean operability = annotation.checkOperability();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String token = request.getHeader("token");
        Long userId = JwtUtil.getUserIdFromToken(token);
        Object[] args = point.getArgs();
        Long postId = getPostIdFromArgs(args);
        if(operability){
            UserPost userPost = userPostMapper.selectOne(new QueryWrapper<UserPost>()
                    .eq("post_id",postId).eq("user_id",userId));
            if(userPost==null){
                return Result.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH);
            }
        }
        return point.proceed(point.getArgs());
    }

    private Long getPostIdFromArgs(Object[] args) {
        for (Object arg : args) {
            if (arg instanceof DelPostDTO) {
                return ((DelPostDTO) arg).getPostId();
            } else if (arg instanceof UpdatePostDTO) {
                return ((UpdatePostDTO) arg).getPostId();
            } else if(arg instanceof Long) {
                return (Long) args[0];
            }
        }
        return null;
    }

}
