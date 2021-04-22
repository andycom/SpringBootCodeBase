package com.fancv.redis.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisServiceTest {

    @Autowired
    RedisService redisService;

    @Test
    public void set() {
        redisService.set();
    }

    @Test
    public void get() {
        redisService.get();
    }
}