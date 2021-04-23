package com.fancv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class SpringBootReport {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootReport.class, args);
    }
}
