package com.fancv.controller;

import com.fancv.permission.PermissionAnnotation;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("user/")
@Api(tags = "1.user")
public class UserListController {


    @PostMapping("list")
    @PermissionAnnotation(permission = "3")
    public List<String> userList(String userId) {
        List<String> r = new ArrayList<String>(10);
        r.add("hello");
        return r;
    }

    @PostMapping("login")
    public String userLogin(@RequestParam(defaultValue = "001") String userId) {

        return "登录成功！";
    }

}
