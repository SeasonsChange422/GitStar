package com.hx.aop;

import com.hx.aop.annotation.UserAuth;
import com.hx.common.Result;
import com.hx.common.enums.AppHttpCodeEnum;
import com.hx.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;

/**
 * @author dhx
 * @date 2025/1/8 22:34
 */
@Component
@Order(1)
@Aspect
public class UserTokenAOP {
    @Pointcut("@annotation(com.hx.aop.annotation.UserAuth)")
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
        UserAuth annotation = method.getAnnotation(UserAuth.class);
        boolean user = annotation.user();
        Object o = null;
        if(user){
            if(token==null){
                return Result.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            }
            if(!JwtUtil.validateToken(token)){
                return Result.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            }
            o = point.proceed(point.getArgs());
        }else {
            o = point.proceed(point.getArgs());
        }
        return o;
    }
}
