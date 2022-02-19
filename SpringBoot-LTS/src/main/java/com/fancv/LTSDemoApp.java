package com.fancv;

import com.github.ltsopensource.spring.boot.annotation.EnableMonitor;
import com.github.ltsopensource.spring.boot.annotation.EnableTaskTracker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication(scanBasePackages = "com.fancv")
@EnableMonitor
@EnableTaskTracker
public class LTSDemoApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(LTSDemoApp.class,args);
        System.out.println( "Hello World!" );
    }
}
