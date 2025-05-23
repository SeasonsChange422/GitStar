package com.hx.aop.annotation;

import org.springframework.core.annotation.AliasFor;

/**
 * @author dhx
 * @date 2025/2/28 10:40
 */
public @interface AdminAuth {
    @AliasFor("admin")
    boolean value() default true;

    @AliasFor("value")
    boolean admin() default true;
}
