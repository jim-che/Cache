package com.chen.cache.config.datasource;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author chenguo
 * @date 2022/3/10 8:40 PM
 */
@Aspect
@Order(-1)
@Component
@Slf4j
public class DynamicDataSourceLog {

    @Before("@annotation(dataSource)")
    public void switchDataSource(JoinPoint joinPoint, DataSource dataSource) {
        if(!DynamicDataSourceContextHolder.containsDataSourceKey(dataSource.value().name())){
            log.error("DataSource [{}] 不存在，使用默认 DataSource [{}] ",
                    dataSource.value(),
                    DynamicDataSourceContextHolder.getDataSourceKey());
        } else {
            DynamicDataSourceContextHolder.setDataSourceKey(dataSource.value().name());
            log.info("切换 DataSource 至 [{}] ，引起切换方法是 [{}]",
                    DynamicDataSourceContextHolder.getDataSourceKey(),
                    joinPoint.getSignature());
        }
    }

    @After("@annotation(dataSource))")
    public void restoreDataSource(JoinPoint point, DataSource dataSource) {
        DynamicDataSourceContextHolder.clearDataSourceKey();
        log.info("重置 DataSource 至 [{}] ，引起重置的方法是 [{}]",
                DynamicDataSourceContextHolder.getDataSourceKey(),
                point.getSignature());
    }

}
