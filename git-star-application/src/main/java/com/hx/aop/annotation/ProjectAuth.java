package com.hx.aop.annotation;

import com.hx.enums.CheckObjectEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author dhx
 * @date 2025/1/9 1:12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ProjectAuth {
    boolean checkOperability() default false;
    boolean checkVisibility() default false;
    CheckObjectEnum checkObject() default CheckObjectEnum.CHECK_PROJECT;
}
