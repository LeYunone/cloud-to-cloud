package com.leyunone.cloudcloud.system.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/6/18 10:30
 */
@Component
public class DelayedQueueConfig {
    
    public static final String EX_DELAYED = "toc.delayed";

    public static final String QUEUE = "toc.delayed.queue";
    
    public static final String R_DELAYED ="toc.delayed.*";
    
    @Bean
    public CustomExchange delayedExchange() {
        Map<String, Object> args = new HashMap<>(1);
        args.put("x-delayed-type", "direct");
        return new CustomExchange(EX_DELAYED, "x-delayed-message", true, false, args);
    }
    
    @Bean
    public Queue delayedQueue() {
        return new Queue(QUEUE); 
    }

    /**
     * 绑定交换机和队列
     */
    @Bean
    public Binding delayedQueueBinding(@Qualifier("delayedQueue") Queue delayedQueue, @Qualifier("delayedExchange") CustomExchange delayedExchange) {
        return BindingBuilder.bind(delayedQueue).to(delayedExchange).with(R_DELAYED).noargs();
    }
}
