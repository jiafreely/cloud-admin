package com.cloud.service.receiver.dead;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: DeadReceiver
 * @description: TODO
 * @date 2021/9/27 14:52
 */

@Component
@RabbitListener(queues = "dead_queue")
public class DeadReceiver {
    @RabbitHandler
    public void process(Map testMessage, Channel channel, Message message) throws IOException {
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        System.out.println("第一个-----DeadtReceiver死信队列消费者收到消息成功  : " + testMessage.toString());
    }
}
