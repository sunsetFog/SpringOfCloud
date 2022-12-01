package com.stars.rabbitmq.all;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
// 完整版的
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

            // 定义路由key
            String routingKey = "order"; // 给队列1、3、4发送消息
            // 交换机的类型
            String type = "direct";
            // 准备消息内容
            String message = "Hello girl.";
            // 准备交换机
            String exchangeName = "direct_all_exchange";
            // 声明交换机
            channel.exchangeDeclare(exchangeName, type, true);
            // 声明队列  参数2：持久化   参数3：排他性   参数4：自动删除  参数5：携带参数
            channel.queueDeclare("queue5", true, false, false, null);
            channel.queueDeclare("queue6", true, false, false, null);
            channel.queueDeclare("queue7", true, false, false, null);
            // 绑定队列和交换机的关系
            channel.queueBind("queue5", exchangeName, "order");
            channel.queueBind("queue6", exchangeName, "order");
            channel.queueBind("queue7", exchangeName, "course");
            // 发送消息给队列queue
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
