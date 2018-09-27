package com.example.livedemo.controller;

import com.example.livedemo.rabbit.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class DemoController {
    @Autowired
    private Sender sender;

    @RequestMapping("mq/{msg}")
    public String mq(@PathVariable String msg) {
        for(int i = 0 ; i < 10 ; i++) {
            int finalI = i;
            new Thread(() -> {
                for (int j = 0; j < 30000; j++) {
                    String uuid = UUID.randomUUID().toString();
                    sender.send(finalI + ":" + uuid);
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        return "success";
    }
}
