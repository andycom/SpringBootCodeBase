package com.fancv.controller;

import com.fancv.service.HelloService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rabbit")
@Api(tags = "1")
public class RabbitMQController {

    @Autowired
    HelloService helloService;


    @GetMapping("hello")
    String sayHello(String message) throws Exception {
        helloService.hello(message);
        return "ok";
    }


}
