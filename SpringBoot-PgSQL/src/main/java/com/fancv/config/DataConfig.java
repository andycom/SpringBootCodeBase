package com.fancv.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DataConfig {
    @Value("${spring.datasource.username}")
    private String user;
    @Value("${spring.datasource.password}")
    private String pass;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Bean(name = "data_pgsql")
    public DataSource dataSource() {
        return getDruidDataSource(user,pass,url,driverClassName);
    }

    private DruidDataSource getDruidDataSource(String name, String password, String url, String driver){
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(driver);
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(name);
        druidDataSource.setPassword(password);
        return druidDataSource;
    }

    @Bean(name = "jdbc_pgsql")
    public JdbcTemplate getJdbcTemplate(@Qualifier("data_pgsql") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}

