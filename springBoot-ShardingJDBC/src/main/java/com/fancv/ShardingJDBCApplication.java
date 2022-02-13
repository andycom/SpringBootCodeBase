package com.fancv;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 */
@MapperScan("com.fancv.shardingdemo.mapper")
@SpringBootApplication
public class ShardingJDBCApplication {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        SpringApplication.run(ShardingJDBCApplication.class, args);
    }
}
