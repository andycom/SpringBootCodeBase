package com.example.demo.datasource;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class }) // 排除 DataSourceAutoConfiguration 的自动配置，避免环形调用
public class DataSourceConfig {
    /**
     * 默认数据源
     *
     * @return
     */
    @Bean(DataSourceConstant.DATA_SOURCE_MASTER)
    @ConfigurationProperties("spring.datasource.druid.master")
    public DataSource dataSourceMaster() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 递增数据源
     *
     * @return
     */
    @Bean(DataSourceConstant.DATA_SOURCE_DB_1)
    @ConfigurationProperties("spring.datasource.druid.db1")
    public DataSource dataSourceDb1() {
        return DruidDataSourceBuilder.create().build();
    }


    /**
     * 设置动态数据源为主数据源
     *
     * @return
     */
    @Bean
    @Primary
    public DynamicDataSource dataSource() {
        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put("master", dataSourceMaster());
        dataSourceMap.put("015", dataSourceDb1());
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        // 使用 Map 保存多个数据源，并设置到动态数据源对象中，这个值最终会在afterPropertiesSet中被设置到resolvedDataSources上
        dynamicDataSource.setTargetDataSources(dataSourceMap);
        return dynamicDataSource;
    }
}
