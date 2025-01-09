package com.hx.aop.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author dhx
 * @date 2025/1/8 22:10
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface UserAuth {
    @AliasFor("user")
    boolean value() default true;

    @AliasFor("value")
    boolean user() default true;
}
