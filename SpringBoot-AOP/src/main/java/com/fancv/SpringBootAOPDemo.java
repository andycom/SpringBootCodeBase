package com.fancv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableSwagger2
public class SpringBootAOPDemo
{
    public static void main( String[] args )
    {

        SpringApplication.run(SpringBootAOPDemo.class, args);

    }
}
