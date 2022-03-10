package com.chen.cache.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.chen.cache.config.datasource.AutoChooseDataSource;
import com.chen.cache.config.datasource.SourceName;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chenguo
 * @date 2022/3/9 4:34 PM
 */

@Configuration
@SuppressWarnings("all")
public class DruidConfig {

    /**
     * 动态数据源配置**********************************↓↓↓↓↓↓↓↓↓↓↓↓↓↓
     ***************************/

    @Bean(name = "write", destroyMethod = "close", initMethod = "init")
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource master() {
        return druidDataSource();
    }

    @Bean(name = "read", destroyMethod = "close", initMethod = "init")
    @ConfigurationProperties(prefix = "spring.datasource.slave01")
    public DataSource slave() {
        return druidDataSource();
    }

    @Bean("dataSource")
    @Primary
    public DataSource autoChooseDataSource() {
        AutoChooseDataSource autoChooseDataSource = new AutoChooseDataSource();
        Map<Object, Object> dataSourceMap = new HashMap<>(2);
        dataSourceMap.put(SourceName.write.value(), master());
        dataSourceMap.put(SourceName.read.value(), slave());
        // 将 read 数据源作为默认指定的数据源
        autoChooseDataSource.setDefaultTargetDataSource(slave());
        // 将 read 和 write 数据源作为指定的数据源
        autoChooseDataSource.setTargetDataSources(dataSourceMap);
        return autoChooseDataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        // 配置事务管理, 使用事务时在方法头部添加@Transactional注解即可
        return new DataSourceTransactionManager(autoChooseDataSource());
    }

    public DataSource druidDataSource() {
        return new DruidDataSource();
    }
}

