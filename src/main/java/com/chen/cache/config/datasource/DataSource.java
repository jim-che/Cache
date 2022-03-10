package com.chen.cache.config.datasource;

import java.lang.annotation.*;

/**
 * @author chenguo
 * @date 2022/3/10 8:37 PM
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {

    /**
     * 数据源名称
     */
    SourceName value();
}
