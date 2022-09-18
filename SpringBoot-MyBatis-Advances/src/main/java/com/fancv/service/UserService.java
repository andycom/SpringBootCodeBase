package com.fancv.service;

import com.fancv.dao.User;
import com.fancv.mapper.MyUserMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@EnableCaching
@CacheConfig(cacheNames = "coffee")
public class UserService {

    @Autowired
    MyUserMapper userMapper;

    /**
     * @Cacheable(cacheNames = "users", condition = "#id > 0", sync = true, keyGenerator = "keyGenerator",key = "'user-' + #id")
     *  Both 'key' and 'keyGenerator' attributes have been set.  不能同时使用
     * @param id
     * @return
     */
    @Cacheable(cacheNames = "users", condition = "#id > 0", sync = true, keyGenerator = "keyGenerator")
    public User getUerInfo(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Cacheable(cacheNames = "cache", sync = true, keyGenerator = "keyGenerator")
    public User getUerInfoWithoutKey() {
        return userMapper.selectByPrimaryKey(2);
    }
}
