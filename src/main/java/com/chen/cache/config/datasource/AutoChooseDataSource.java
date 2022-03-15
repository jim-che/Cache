package com.chen.cache.config.datasource;


import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import reactor.util.annotation.NonNullApi;

import javax.sql.DataSource;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @author chenguo
 * @date 2022/3/10 8:22 PM
 */

public class AutoChooseDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceContextHolder.getDataSourceKey();
    }

    @Override
    protected DataSource determineTargetDataSource() {
        return super.determineTargetDataSource();
    }

    @Override
    public void setDefaultTargetDataSource(Object defaultDataSource) {
        super.setDefaultTargetDataSource(defaultDataSource);
    }

    @Override
    public void setTargetDataSources(Map<Object, Object> dataSources) {
        super.setTargetDataSources(dataSources);
        // 将数据源的 key 放到数据源上下文的 key 集合中，用于切换时判断数据源是否有效
        DynamicDataSourceContextHolder.addDataSourceKeys(dataSources.keySet());
    }
}
