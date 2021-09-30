package com.cloud.service.receiver.headers;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: HeadersReceiver
 * @description: TODO
 * @date 2021/9/28 9:59
 */
@Component
@RabbitListener(queues = "HEADERS_QUEUE")
public class HeadersReceiver {
    @RabbitHandler
    public void process(byte[] message) {
        System.out.println("receive : HeadersQueue:" + new String(message));
    }
}
