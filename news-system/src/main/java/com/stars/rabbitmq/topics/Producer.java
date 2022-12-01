package com.stars.rabbitmq.topics;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
/*
主题模式
*/
public class Producer {
    public static void main(String[] args) {
        // 1.创建连接工程
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("39.108.174.145");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
        connectionFactory.setVirtualHost("/");

        Connection newConnection = null;
        Channel channel = null;
        try {
            // 2.创建connection
            newConnection = connectionFactory.newConnection("生产者");
            // 3.通过连接获取通道Channel
            channel = newConnection.createChannel();

            /*
                定义路由key
                *代至少1级   #代表多个（包括0个）
                队列1：com.#
                队列2：*.course.*
                队列3：#.order.#
                队列4：#.user.*
            */
            String routingKey = "com.order.user.test"; // 给队列1、3、4发送消息
            // 交换机的类型
            String type = "topic";// topic主题模式,routingKey是模糊匹配
            // 5.准备消息内容
            String message = "Hello boy!";
            // 6.准备交换机
            String exchangeName = "topic_exchange";
            // 7.发送消息给队列queue
            // 参数1：交换机   参数2：队列名称/routingKey   参数3：属性配置   参数4：发送消息内容
            channel.basicPublish(exchangeName, routingKey, null, message.getBytes());
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
