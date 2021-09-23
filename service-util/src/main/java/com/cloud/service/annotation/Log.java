package com.cloud.service.annotation;

import java.lang.annotation.*;

/**
 * @description: 自定义操作日志记录注解
 * @param: null
 * @return:
 * @author: xjh
 * @date: 2021/9/18 16:50
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    /**
     * 模块
     */
    public String title() default "";

}
