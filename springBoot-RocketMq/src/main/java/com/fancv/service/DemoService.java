package com.fancv.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
@Slf4j
public class DemoService {

    public String getStart(String num) {

        System.out.println("业务逻辑开始运行");
        log.info("this num :{}", num);
        return "ok";
    }
}
