package com.fancv.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@Api(tags = "1")
public class TestController {

    @Autowired
    @Qualifier("jdbc_pgsql")
    private JdbcTemplate jdbcTemplate;

    @GetMapping(value = "/getUser")
    public Object get() {

        LocalDate date = LocalDate.now();

        System.out.println(date.toString());
        String sql = "select * from public.user";
        return jdbcTemplate.queryForList(sql);
    }
}

