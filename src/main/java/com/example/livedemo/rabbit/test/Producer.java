package com.example.livedemo.rabbit.test;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;
public class Producer {

    public static void main(String[] args) throws IOException, TimeoutException {
        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("admin");
        factory.setPassword("12345");
        //设置 RabbitMQ 地址
        factory.setHost("localhost");
        //建立到代理服务器到连接
        Connection conn = factory.newConnection();
        //获得信道
        Channel channel = conn.createChannel();
        //声明交换器
        String exchangeName = "hello-exchange";
        channel.exchangeDeclare(exchangeName, "direct", true);

        String routingKey = "hola";
        byte[] messageBodyBytes = "start".getBytes();
        channel.basicPublish(exchangeName, routingKey, null, messageBodyBytes);
        try {
            while (true) {
                Thread.sleep(1000);
                String uuid = UUID.randomUUID().toString().replace("-", "");
                //发布消息
                messageBodyBytes = uuid.getBytes();
                channel.basicPublish(exchangeName, routingKey, null, messageBodyBytes);

            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            channel.close();
            conn.close();
        }
    }
}