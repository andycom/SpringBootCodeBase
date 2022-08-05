package com.fancv.controller;

import com.fancv.dao.User;
import com.fancv.mapper.MyUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("mybatis")
public class DemoController {
    @Autowired
    MyUserMapper userMapper;
    @GetMapping("insert")
    public String batchInster(){

        User user = userMapper.selectByPrimaryKey(1);
       return user.getName();
    }

    @GetMapping("replace")
    public int Replace(){
        List<User> users= new ArrayList<>();
        User a= new User();
        a.setAge(10);
        a.setId(3);
        a.setEmail("dshkfjh");
        a.setModifyTime(new Date());
        a.setCreateTime(new Date());
        a.setName("dfsd");
        a.setPassword("124321");
        users.add(a);
        return userMapper.updateOrInsertClientInfo(users);
    }

    @GetMapping("merge")
    public int merge(){
        User a= new User();
        a.setAge(10);
        a.setId(3);
        a.setEmail("dshkfjh");
        a.setModifyTime(new Date());
        a.setCreateTime(new Date());
        a.setName("dfa");
        a.setPassword("124321");
        return userMapper.mergeinfo(a);
    }
}
