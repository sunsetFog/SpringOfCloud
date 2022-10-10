package com.core.service;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = {"duanxin.fanout.queue"})
public class DuanxinFanoutConsumer {
    @RabbitHandler
    public void reviceMessage(String message) {
        System.out.println("duanxin.fanout.queue---接收到了订单信息是："+message);
    }
}
