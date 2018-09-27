package com.example.livedemo.rabbit;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Sender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send(String msg) {
//        System.out.println("Sender : " + msg);
        this.rabbitTemplate.convertAndSend("hello", msg);
    }

}