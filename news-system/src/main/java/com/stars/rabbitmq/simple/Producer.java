package com.stars.rabbitmq.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/*
简单模式
生产者
所有的中间件技术都是基于tcp/ip协议基础之上构建新型的协议规范，只不过rabbitmq遵循的是amqp
协议都是有ip的port端口
*/
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
            // 通过创建交换机，声明队列，绑定关系，路由key，发送消息，和接收消息
            // 参数1：队列名称  参数2：持久化   参数3：排他性  参数4：自动删除
            // 持久化就是服务器重启后队列依然还在
            // 非持久化和持久化都会存盘，等待接收消息
            String queueName = "queue1";
            channel.queueDeclare(queueName, false, false, false, null);
            // 准备消息内容
            String message = "Hello boy!";
            // 发送消息给队列queue
            // 参数1：交换机   参数2：队列名称/routingKey   参数3：属性配置   参数4：发送消息内容
            channel.basicPublish("", queueName, null, message.getBytes());
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
