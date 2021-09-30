package com.cloud.service.receiver.direct;

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
 * @ClassName: DirectReceiver
 * @description:
 * @date 2021/9/23 16:56
 */

@Component
@RabbitListener(queues = "TestDirectQueue")//监听的队列名称 TestDirectQueue
public class DirectReceiver {
    @RabbitHandler
    public void process(Map testMessage, Channel channel, Message message) throws IOException {
        //拿到消息延迟消费
        try {
            Thread.sleep(1000 * 1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            System.out.println("第一个-----DirectReceiver消费者收到消息成功  : " + testMessage.toString());
        } catch (Exception e) {
            //消息的标志,是否批量处理,是否重入队列
            //消费者处理出了问题，需要告诉队列信息消费失败
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            System.err.println("第一个-----DirectReceiver消费者收到消息失败  : " + testMessage.toString());
        }

    }
}
