package com.fancv.service;

import com.fancv.rabbit.hello.HelloSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelloService {


    @Autowired
    private HelloSender helloSender;


    public void hello(String message) throws Exception {
        helloSender.send(message);
    }
}
