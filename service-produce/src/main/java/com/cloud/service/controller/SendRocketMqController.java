//package com.cloud.service.controller;
//
//import com.cloud.service.result.R;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiParam;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.rocketmq.client.producer.SendCallback;
//import org.apache.rocketmq.client.producer.SendResult;
//import org.apache.rocketmq.spring.core.RocketMQTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.messaging.support.MessageBuilder;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * @author xjh
// * @version 1.0
// * @ClassName: SendRocketMqController
// * @description: TODO
// * @date 2021/10/15 9:42
// */
//@Slf4j
//@RestController
//@Api(description = "RocketMq队列")
//@RequestMapping("/admin/rocketMq/message")
//public class SendRocketMqController {
//    @Autowired
//    private RocketMQTemplate rocketMQTemplate;
//    @Value("${rocketmq.producer.sendMsgTimeOut}")
//    private Integer messageTimeOut;
//
//    /**
//     * 发送简单的MQ消息
//     *
//     * @param msg
//     * @return
//     */
//    @PostMapping("/sendMessage")
//    @ApiParam("简单的MQ消息")
//    public R send(@ApiParam(value = "发送的消息内容", required = true) @RequestParam("msg") String msg) {
//        if (StringUtils.isEmpty(msg)) {
//            return new R().message("msg不能为空");
//        }
//        log.info("发送MQ消息内容：" + msg);
//        rocketMQTemplate.syncSend("queue_test_topic", MessageBuilder.withPayload(msg).build());
//        return new R().success(true);
//    }
//
//    /**
//     * 发送异步消息 在SendCallback中可处理相关成功失败时的逻辑
//     */
//    @PostMapping("/sendAsyncMsg")
//    @ApiParam("发送异步消息")
//    public R sendAsyncMsg(@ApiParam(value = "发送的消息内容", required = true) @RequestParam("msgBody") String msgBody) {
//        rocketMQTemplate.asyncSend("queue_test_topic", MessageBuilder.withPayload(msgBody).build(), new SendCallback() {
//            @Override
//            public void onSuccess(SendResult sendResult) {
//                // 处理消息发送成功逻辑
//                log.info("发送异步消息成功");
//            }
//
//            @Override
//            public void onException(Throwable e) {
//                // 处理消息发送异常逻辑
//                log.info("发送异步消息失败");
//            }
//        });
//        return new R().success(true);
//    }
//
//    /**
//     * 发送延时消息<br/>
//     * 在start版本中 延时消息一共分为18个等级分别为：1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h<br/>
//     */
//    @PostMapping("/sendDelayMsg")
//    @ApiParam("发送延时消息")
//    public R sendDelayMsg(@ApiParam(value = "发送的消息内容", required = true) @RequestParam("msgBody") String msgBody,
//                          @ApiParam(value = "登记", required = true) @RequestParam("delayLevel") Integer delayLevel) {
//        rocketMQTemplate.syncSend("queue_test_topic", MessageBuilder.withPayload(msgBody).build(), messageTimeOut, delayLevel);
//        return new R().success(true);
//    }
//
//    /**
//     * 发送带tag的消息,直接在topic后面加上":tag"
//     */
//    @PostMapping("/sendTagMsg")
//    @ApiParam("发送带tag的消息")
//    public R sendTagMsg(@ApiParam(value = "发送的消息内容", required = true) @RequestParam("msgBody") String msgBody) {
//        rocketMQTemplate.syncSend("queue_test_topic:tag1", MessageBuilder.withPayload(msgBody).build());
//        return new R().success(true);
//    }
//}
