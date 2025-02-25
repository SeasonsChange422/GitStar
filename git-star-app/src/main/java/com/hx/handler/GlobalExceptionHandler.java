package com.hx.handler;

import com.hx.common.Result;
import com.hx.common.enums.AppHttpCodeEnum;
import com.hx.exception.SystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author dhx
 * @date 2025/1/9 0:40
 */
public class GlobalExceptionHandler {
    @ExceptionHandler(SystemException.class)
    public Result systemExceptionHandler(SystemException e){
        e.printStackTrace();
        return Result.errorResult(e.getCode(),e.getMsg());
    }


    @ExceptionHandler(Exception.class)
    public Result exceptionHandler(Exception e){
        e.printStackTrace();
        return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),e.getMessage());
    }
}
