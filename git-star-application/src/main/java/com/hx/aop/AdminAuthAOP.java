package com.hx.aop;

import com.hx.aop.annotation.AdminAuth;
import com.hx.common.Result;
import com.hx.common.enums.AppHttpCodeEnum;
import com.hx.mapper.UserMapper;
import com.hx.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;

/**
 * @author dhx
 * @date 2025/2/28 10:41
 */
public class AdminAuthAOP {
    @AdminAuth
    private UserMapper userMapper;
    @Pointcut("@annotation(com.hx.aop.annotation.AdminAuth)")
    public void loginPointCut(){

    }

    @Around("loginPointCut()")
    public Object tokenHandler(ProceedingJoinPoint point) throws Throwable {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String token = request.getHeader("token");
        Class<?> aClass = point.getTarget().getClass();
        MethodSignature ms = (MethodSignature)point.getSignature();
        Method method = aClass.getDeclaredMethod(ms.getName(),ms.getParameterTypes());
        AdminAuth annotation = method.getAnnotation(AdminAuth.class);
        boolean admin = annotation.admin();
        Object o = null;
        if(admin){
            if(token==null){
                return Result.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            }
            if(!JwtUtil.validateToken(token)){
                return Result.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            }
            if(!userMapper.selectById(JwtUtil.getUserIdFromToken(token)).getIsAdmin()){
                return Result.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH);
            }
            o = point.proceed(point.getArgs());
        }else {
            o = point.proceed(point.getArgs());
        }
        return o;
    }
}
