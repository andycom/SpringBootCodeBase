package com.fancv.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user/")
public class UserInfoController {

    @GetMapping("list")
    public String  userList(){
        return "ok";
    }
}
