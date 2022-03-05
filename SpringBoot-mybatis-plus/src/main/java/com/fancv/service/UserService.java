package com.fancv.service;

import com.fancv.dao.User;
import com.fancv.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;


    public Integer addUser(User user) {
        return userMapper.insert(user);

    }

    public List<User> getUsers() {
        java.util.List<User> result = userMapper.selectList(null);
        return result;
    }

}
