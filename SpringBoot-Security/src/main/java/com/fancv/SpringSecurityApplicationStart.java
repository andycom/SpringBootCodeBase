package com.fancv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableResourceServer
public class SpringSecurityApplicationStart
{
    public static void main( String[] args )
    {
        SpringApplication.run(SpringSecurityApplicationStart.class,args);
    }
}
