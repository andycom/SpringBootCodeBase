package com.example.demo.controller;

import com.example.demo.service.impl.UserService;
import com.example.demo.utils.BaseUserDTO;
import com.example.demo.utils.UserContext;
import org.aspectj.lang.annotation.AdviceName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("1")
public class DemoController {


    @Autowired
    UserService userService;

    @GetMapping ("login")
    public String userLogin(@RequestParam(defaultValue = "001") String userId) {
        System.out.println("根据token信息查询用户信息");

        BaseUserDTO baseUserDTO = new BaseUserDTO();
        baseUserDTO.setId(015);
        baseUserDTO.setUserNmae("015");
        baseUserDTO.setPermissions(new ArrayList<>());
        // 校验权限
        System.out.println("根据userId 查询权限");
        baseUserDTO.addPeemission("1");
        baseUserDTO.addPeemission("2");

        UserContext.setBaseUser(baseUserDTO);

        return userService.login();
    }

    @GetMapping ("master")
    public String userLogin2(@RequestParam(defaultValue = "001") String userId) {
        System.out.println("根据token信息查询用户信息");

        BaseUserDTO baseUserDTO = new BaseUserDTO();
        baseUserDTO.setId(015);
        baseUserDTO.setUserNmae("master");
        baseUserDTO.setPermissions(new ArrayList<>());
        // 校验权限
        System.out.println("根据userId 查询权限");
        baseUserDTO.addPeemission("1");
        baseUserDTO.addPeemission("2");

        UserContext.setBaseUser(baseUserDTO);

        return userService.login();
    }
}
