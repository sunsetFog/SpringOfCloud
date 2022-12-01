package com.stars.rabbitmq.work.lunnxun;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

// work工作模式-轮询模式     两个消费者一个个分配，性能不好分的也是这么多
public class Producer {
    public static void main(String[] args) {
        // 创建连接工程
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("39.108.174.145");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
        connectionFactory.setVirtualHost("/");

        Connection newConnection = null;
        Channel channel = null;
        try {
            // 创建connection
            newConnection = connectionFactory.newConnection("生产者");
            // 通过连接获取通道Channel
            channel = newConnection.createChannel();
            for (int i = 0; i < 20; i++) {
                String message = "Hello girl：" + i;
                // 走默认交换机
                channel.basicPublish("", "queue1", null, message.getBytes());
            }
            System.out.println("消息发送成功！");
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接
            if(channel != null && channel.isOpen()) {
                try {
                    channel.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // 关闭通道
            if(newConnection != null && newConnection.isOpen()) {
                try {
                    newConnection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
