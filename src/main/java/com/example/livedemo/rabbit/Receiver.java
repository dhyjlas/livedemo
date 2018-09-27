package com.example.livedemo.rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class Receiver {
    private static AtomicInteger sum = new AtomicInteger(0);

    @RabbitListener(queues = "hello")
    public void processMessage1(String msg) throws InterruptedException {
        int i = sum.incrementAndGet();
        System.out.println(i + "\t\t" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()) + "：" + msg);
    }
}