package com.example.demo.service.impl;

import com.example.demo.mapper.SysDictMapper;
import com.example.demo.model.SysDict;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;

@Component
public class UserService {

    @Resource
    private SysDictMapper sysDictMapper;

    public String login(){

        SysDict s = sysDictMapper.getDictByCode("1");
        return s.toString();
    }
}
