package com.chen.cache.config.bloom;

import java.lang.annotation.*;

/**
 * @author chenguo
 * @date 2022/3/17 4:32 下午
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Bloom {
    String method() default "";
    String bean() default "";
}
