package com.fancv.controller;

import com.alibaba.fastjson.JSON;
import com.fancv.login.BaseUserDTO;
import com.fancv.permission.PermissionAnnotation;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("user/")
@Api(tags = "1.user")
@Slf4j
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

    @PostMapping("login_1")
    public String login(@RequestBody @Validated BaseUserDTO baseUserDTO) {

        log.info(JSON.toJSONString(baseUserDTO));
        return "OK";
    }

}
