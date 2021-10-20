//package com.cloud.service.rocketReceiver;
//
///**
// * @author xjh
// * @version 1.0
// * @ClassName: RocketMQMsgListener
// * @description: TODO
// * @date 2021/10/15 15:40
// * <p>
// * rocketmq 消息监听，@RocketMQMessageListener中的selectorExpression为tag，默认为*
// */
///**
// * rocketmq 消息监听，@RocketMQMessageListener中的selectorExpression为tag，默认为*
// */
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.rocketmq.common.message.MessageExt;
//import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
//import org.apache.rocketmq.spring.core.RocketMQListener;
//import org.apache.rocketmq.spring.core.RocketMQTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//
///**
// //topic：和消费者发送的topic相同
// //tag
// //group：不用和生产者group相同
// */
//@Slf4j
//@Component
//@RocketMQMessageListener(topic = "queue_test_topic", selectorExpression = "*", consumerGroup = "queue_group_test")
//public class RocketMQMsgListener implements RocketMQListener<MessageExt> {
//    @Autowired
//    private RocketMQTemplate rocketMQTemplate;
//
//    @Override
//    public void onMessage(MessageExt message) {
//        byte[] body = message.getBody();
//        String msg = new String(body);
//        log.info("接收到消息：{}", msg);
//    }
//}
