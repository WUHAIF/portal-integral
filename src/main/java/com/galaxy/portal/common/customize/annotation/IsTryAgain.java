package com.galaxy.portal.common.customize.annotation;

import io.swagger.models.auth.In;

import java.lang.annotation.*;

/**
 * @Author: wuhaifeng
 * @DateTime: 2021/4/1 23:43
 * @Description: 使用重试方法
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IsTryAgain {
    int maxRetries() default 3;
}
