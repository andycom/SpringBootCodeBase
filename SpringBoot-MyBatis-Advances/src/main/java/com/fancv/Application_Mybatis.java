package com.fancv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//TODO

/**
 * 1.mybitais 拦截器demo
 * 2.mybatis aop 多数据源
 * 3.数据库连接池
 * 4.分库分表
 */
@SpringBootApplication
@EnableSwagger2
public class Application_Mybatis {

    public static void main(String[] args) {
        SpringApplication.run(Application_Mybatis.class, args);
    }
}
