package com.stars.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RabbitmqOrderService {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    /*
        模拟用户下单
    */
    public void makeOrder(String userid, String productid, int num) {
        // 随机订单id
        String orderId = UUID.randomUUID().toString();
        System.out.println("订单生成成功："+orderId);
        // 消息分发
        String exchangeName = "fanout_order_exchange";
        String routingKey = "";
        rabbitTemplate.convertAndSend(exchangeName, routingKey, orderId);
        // 方式2：5秒后过期删除消息  没有TTL过期队列标记
//        MessagePostProcessor messagePostProcessor = new MessagePostProcessor() {
//            @Override
//            public Message postProcessMessage(Message message) throws AmqpException {
//                message.getMessageProperties().setExpiration("5000");
//                message.getMessageProperties().setContentEncoding("UTF-8");
//                return message;
//            }
//        };
//        rabbitTemplate.convertAndSend("direct_order_exchange", "sms", orderId, messagePostProcessor);
    }
}
