package com.cloud.service.config.rabbit;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: RabbitConfig
 * @description: url https://blog.csdn.net/qq_35387940/article/details/100514134
 * @date 2021/9/24 14:49
 */
@Configuration(proxyBeanMethods = false)
public class RabbitConfig {

    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        //设置开启Mandatory,才能触发回调函数,无论消息推送结果怎么样都强制调用回调函数
        rabbitTemplate.setMandatory(true);

        /**
         * @description: 交换机
         *如果消息没有到exchange, 则confirm回调, ack=false; 如果消息到达exchange, 则confirm回调, ack=true
         * @param: connectionFactory
         * @return: org.springframework.amqp.rabbit.core.RabbitTemplate
         * @author: xjh
         * @date: 2021/9/24 14:56
         */
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                System.out.println("ConfirmCallback:     " + "相关数据：" + correlationData);
                System.out.println("ConfirmCallback:     " + "确认情况：" + ack);
                System.out.println("ConfirmCallback:     " + "原因：" + cause);
            }
        });

        /**
         * @description: 队列
         * 如果exchange到queue成功, 则不回调return; 如果exchange到queue失败, 则回调return(需设置mandatory=true, 否则不会回调, 消息就丢了)
         * @param: connectionFactory
         * @return: org.springframework.amqp.rabbit.core.RabbitTemplate
         * @author: xjh
         * @date: 2021/9/24 14:57
         */
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                System.out.println("ReturnCallback:     " + "消息：" + message);
                System.out.println("ReturnCallback:     " + "回应码：" + replyCode);
                System.out.println("ReturnCallback:     " + "回应信息：" + replyText);
                System.out.println("ReturnCallback:     " + "交换机：" + exchange);
                System.out.println("ReturnCallback:     " + "路由键：" + routingKey);
            }
        });

        return rabbitTemplate;
    }
}
