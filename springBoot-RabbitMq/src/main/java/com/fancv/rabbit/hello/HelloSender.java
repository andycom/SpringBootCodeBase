package com.fancv.rabbit.hello;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class HelloSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send(String message) {
        String context = message + new Date();
        System.out.println("Sender : " + context);
        try {
            this.rabbitTemplate.convertAndSend("hello", context);
        } catch (AmqpException e) {
            log.error("hello 消息发送失败", e);
        }

    }

}