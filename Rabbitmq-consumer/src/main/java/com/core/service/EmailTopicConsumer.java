package com.core.service;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Service;

@Service
// 方式2：注解建队列、交换机，绑定关系
// 消费者和生产者都能建队列，交换机     消费者先启动需要找到队列。。所以最好在消费者里建
@RabbitListener(bindings = @QueueBinding(
        value = @Queue(value = "email.topic.queue", durable = "true", autoDelete = "false"),
        exchange = @Exchange(value = "topic_order_exchange", type = ExchangeTypes.TOPIC),
        key = "#.email.#"
))
public class EmailTopicConsumer {
    @RabbitHandler
    public void reviceMessage(String message) {
        System.out.println("email.topic.queue---接收到了订单信息是："+message);
    }
}
