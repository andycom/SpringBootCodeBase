package com.fancv.controller;

import com.fancv.dao.User;
import com.fancv.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "1")
@RequestMapping("1")
public class MybatisPlusController {


    @Autowired
    UserService userService;

    @PostMapping("get")
    public List<User> get(){
        return userService.getUsers();
    }
    @PostMapping("add")
    public Integer add(@RequestBody User user){
        return userService.addUser(user);
    }
}
