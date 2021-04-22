package com.fancv.redis.service;

import com.fancv.redis.utils.RedisCacheUtil;
import org.springframework.stereotype.Service;

@Service
public class RedisService {


    void set() {
        RedisCacheUtil.getRedis().set("1", 2);
        RedisCacheUtil.getRedis().set("2", 2);
        RedisCacheUtil.getRedis().set("3", 2);


    }

    void get() {
        Object s = RedisCacheUtil.getRedis().get("1");
        Integer a = (Integer) s;
        System.out.println("get: " + a);
        RedisCacheUtil.getRedis().set("5", 2);
        RedisCacheUtil.getRedis().set("6", 2);


    }


}
