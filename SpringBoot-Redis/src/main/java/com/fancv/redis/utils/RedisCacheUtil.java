package com.fancv.redis.utils;

import com.fancv.redis.redis.Redis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author hamish-wu
 */
@Component
public class RedisCacheUtil {

    static RedisCacheUtil redisCacheUtil;

    @PostConstruct
    public void init() {
        redisCacheUtil = this;
    }


    @Autowired
    Redis redis;

    public static Redis getRedis() {
        return redisCacheUtil.redis;
    }

}
