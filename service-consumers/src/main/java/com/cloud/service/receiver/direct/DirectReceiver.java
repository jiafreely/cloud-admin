package com.cloud.service.receiver.direct;

import org.springframework.stereotype.Component;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: DirectReceiver
 * @description:
 * @date 2021/9/23 16:56
 */

@Component
//@RabbitListener(queues = "TestDirectQueue")//监听的队列名称 TestDirectQueue
public class DirectReceiver {
    //@RabbitHandler
    //public void process(Map testMessage) {
    //    System.out.println("第一个-----DirectReceiver消费者收到消息  : " + testMessage.toString());
    //}
}
