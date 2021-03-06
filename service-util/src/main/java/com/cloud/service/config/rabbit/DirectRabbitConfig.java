package com.cloud.service.config.rabbit;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: DirectRabbitConfig
 * 直连型交换机
 * @description: url https://blog.csdn.net/qq_35387940/article/details/100514134
 * @date 2021/9/23 16:26
 */
@Configuration
public class DirectRabbitConfig {
    //队列过期时间
    private int testDirectQueueTTL = 5000;

    //队列 起名：TestDirectQueue
    @Bean
    public Queue TestDirectQueue() {
        // durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
        // exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
        // autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
        //   return new Queue("TestDirectQueue",true,true,false);

        //一般设置一下队列的持久化就好,其余两个就是默认false
        //return new Queue("TestDirectQueue", true);

        return QueueBuilder.durable("TestDirectQueue")
                .ttl(testDirectQueueTTL)
                .deadLetterRoutingKey("dead_route_key")//设置死信队列的RouteKey
                .deadLetterExchange("dead_exchange")//设置死信队列的Exchange
                .build();
    }

    //Direct交换机 起名：TestDirectExchange
    @Bean
    DirectExchange TestDirectExchange() {
        //  return new DirectExchange("TestDirectExchange",true,true);
        return new DirectExchange("TestDirectExchange", true, false);
    }

    //绑定  将队列和交换机绑定, 并设置用于匹配键：TestDirectRouting
    @Bean
    Binding bindingDirect() {
        return BindingBuilder.bind(TestDirectQueue()).to(TestDirectExchange()).with("TestDirectRouting");
    }
}
