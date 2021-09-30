package com.cloud.service.config.rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: DlRabbitConfig
 * @description: 配置死信队列
 * @date 2021/9/27 11:40
 */
@Configuration
public class DeadRabbitConfig {

    /**
     * 死信队列
     *
     * @return
     */
    @Bean
    public Queue deadQueue() {
        return new Queue("dead_queue", true);
    }

    /**
     * 死信交换机
     *
     * @return
     */
    @Bean
    public DirectExchange deadExchange() {
        return new DirectExchange("dead_exchange");
    }

    /**
     * 死信队列绑定死信交换机
     *
     * @param dlxQueue
     * @param dlxExchange
     * @return
     */
    @Bean
    public Binding deadBinding() {
        return BindingBuilder.bind(deadQueue()).to(deadExchange()).with("dead_route_key");
    }
}
