package com.cloud.service.receiver.confirm;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: ConfirmReceiveListener
 * @description: TODO
 * @date 2021/9/26 16:12
 */
@Component
@RabbitListener(queues = "confirm_queue")
public class ConfirmReceiveListener {
    @RabbitHandler
    public void receiveMsg(Map testMessage) {
        System.out.println("接收到的消息为：" + testMessage.toString());
    }
}
