package com.fancv.controller;

import com.fancv.dao.User;
import com.fancv.mapper.MyUserMapper;
import com.fancv.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.accept.MappingMediaTypeFileExtensionResolver;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("mybatis")
@Api(value = "User")
public class DemoController {

    @Autowired
    MyUserMapper userMapper;

    @Autowired
    UserService userService;


    @GetMapping("user_info")
    public User getUserInfo(Integer id){

        User user = userService.getUerInfo(id);
        User user1 = userService.getUerInfoWithoutKey();
        return user;
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
        return 1;
    }
}
