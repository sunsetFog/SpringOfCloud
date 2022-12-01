package com.stars.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
/**
 * study: rabbitmq
 * 修改了配置，删掉队列，交换机，再运行
 */
@Configuration
public class RabbitmqConfig {
    // 声明注册fanout模式的交换机
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanout_order_exchange", true, false);
    }
    // 声明队列
    @Bean
    public Queue smsFanoutQueue() {
        return new Queue("sms.fanout.queue", true);
    }
    @Bean
    public Queue duanxinFanoutQueue() {
        return new Queue("duanxin.fanout.queue", true);
    }
    @Bean
    public Queue emailFanoutQueue() {
        return new Queue("email.fanout.queue", true);
    }
    // 队列和交换机完成绑定关系
    @Bean
    public Binding smsFanoutBangding() {
        return BindingBuilder.bind(smsFanoutQueue()).to(fanoutExchange());
    }
    @Bean
    public Binding duanxinFanoutBangding() {
        return BindingBuilder.bind(duanxinFanoutQueue()).to(fanoutExchange());
    }
    @Bean
    public Binding emailFanoutBangding() {
        return BindingBuilder.bind(emailFanoutQueue()).to(fanoutExchange());
    }


    // 声明注册direct模式的交换机
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("direct_order_exchange", true, false);
    }
    // 声明队列
    @Bean
    public Queue smsDirectQueue() {
//        return new Queue("sms.direct.queue", true);
        HashMap<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 5000);// 方式1：5秒后过期删除消息  有TTL过期队列标记
        args.put("x-dead-letter-exchange", "dead_exchange");// 5秒后把消息发送给死信交换机
        args.put("x-dead-letter-routing-key", "dead");// fanout不需要配置
//        args.put("x-max-length", 10);// 设置最大消息数量
        return new Queue("sms.direct.queue", true, false, false, args);
    }
    // 队列和交换机完成绑定关系
    @Bean
    public Binding smsDirectBangding() {
        // direct路由模式，需要绑定路由key
        return BindingBuilder.bind(smsDirectQueue()).to(directExchange()).with("sms");
    }

    // 声明注册dead死信交换机
    @Bean
    public DirectExchange deadExchange() {
        return new DirectExchange("dead_exchange", true, false);
    }
    // 声明死信队列
    @Bean
    public Queue deadDirectQueue() {
        return new Queue("dead.direct.queue", true);
    }
    // 队列和交换机完成绑定关系
    @Bean
    public Binding deadDirectBangding() {
        // direct路由模式，需要绑定路由key
        return BindingBuilder.bind(deadDirectQueue()).to(deadExchange()).with("dead");
    }
}
