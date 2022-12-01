package com.stars.rabbitmq.work.fair;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer02 {
    public static void main(String[] args) {
        // 创建连接工程
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // 设置连接属性
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
            // 指标定义出来，qos=1
            channel.basicQos(1);
            String queueName = "queue1";
            // 通过创建交换机，声明队列，绑定关系，路由key，发送消息，和接收消息
            Channel finalChannel = channel;
            channel.basicConsume(queueName, false, new DeliverCallback() {// false手动应答  true自动应答
                @Override
                public void handle(String s, Delivery delivery) throws IOException {
                    try {
                        System.out.println();
                        System.out.println("Consumer02：收到消息是" + new String(delivery.getBody(), "UTF-8"));
                        Thread.sleep(1000);
                        // 单条消费 同一时刻，服务器只会推送一条消息给消费者 java: 从内部类引用的本地变量必须是最终变量或实际上的最终变量
                        finalChannel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, new CancelCallback() {
                @Override
                public void handle(String s) throws IOException {
                    System.out.println("接收消息失败...");
                }
            });
            System.out.println(queueName + "：开始接收消息");
            System.in.read();
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
