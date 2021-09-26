package com.cloud.service.config.rabbit;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: ConfirmRabbitConfig
 * @description: confirm机制 消息确认队列
 * @date 2021/9/26 16:02
 */
@Configuration
public class ConfirmRabbitConfig {
    @Bean
    public Queue ConfirmQueue() {
        // durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
        // exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
        // autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
        return new Queue("confirm_queue", true);
    }
}