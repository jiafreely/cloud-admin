package com.cloud.service.controller;

import cn.hutool.core.lang.UUID;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: SendMessageController
 * @description: convertAndSend 方法时的结果：输出时没有顺序，不需要等待，直接运行
 * 使用 convertSendAndReceive 方法时的结果：按照一定的顺序，只有确定消费者接收到消息，才会发送下一条信息，每条消息之间会有间隔时间
 * @date 2021/9/23 16:32
 */
@Slf4j
@RestController
@Api(description = "队列")
@RequestMapping("/admin/produce/message")
public class SendMessageController {
    //使用RabbitTemplate,这提供了接收/发送等等方法
    @Autowired
    RabbitTemplate rabbitTemplate;

    @PostMapping("/sendDirectMessage")
    @ApiOperation("发送消息")
    public String sendDirectMessage(@ApiParam(value = "发送消息数", required = true) @RequestParam("number") Integer number) {
        for (int i = 1; i <= number; i++) {
            String messageId = String.valueOf(UUID.randomUUID());
            String messageData = Integer.toString(i);
            String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            Map<String, Object> map = new HashMap<>();
            map.put("messageId", messageId);
            map.put("messageData", messageData);
            map.put("createTime", createTime);
            //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
            rabbitTemplate.convertAndSend("TestDirectExchange", "TestDirectRouting", map);
        }
        return "ok";
    }

    @GetMapping("/sendTopicMessage1")
    public String sendTopicMessage1() {
        for (int i = 1; i <= 6; i++) {
            String messageId = String.valueOf(UUID.randomUUID());
            String messageData = "message: M A N ";
            String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            Map<String, Object> manMap = new HashMap<>();
            manMap.put("messageId", messageId);
            manMap.put("messageData", messageData);
            manMap.put("createTime", createTime);
            rabbitTemplate.convertAndSend("topicExchange", "topic.man", manMap);
        }
        return "ok";
    }

    @GetMapping("/sendTopicMessage2")
    public String sendTopicMessage2() {
        for (int i = 1; i <= 6; i++) {
            String messageId = String.valueOf(UUID.randomUUID());
            String messageData = "message: woman is all ";
            String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            Map<String, Object> womanMap = new HashMap<>();
            womanMap.put("messageId", messageId);
            womanMap.put("messageData", messageData);
            womanMap.put("createTime", createTime);
            rabbitTemplate.convertAndSend("topicExchange", "topic.woman", womanMap);
        }
        return "ok";
    }

    @GetMapping("/sendFanoutMessage")
    public String sendFanoutMessage() {
        for (int i = 1; i <= 2; i++) {
            String messageId = String.valueOf(UUID.randomUUID());
            String messageData = "message: testFanoutMessage ";
            String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            Map<String, Object> map = new HashMap<>();
            map.put("messageId", messageId);
            map.put("messageData", messageData);
            map.put("createTime", createTime);
            rabbitTemplate.convertSendAndReceive("fanoutExchange", null, map);
        }
        return "ok";
    }

    /**
     * @description: 消息确认队列
     * @return: java.lang.String
     * @author: xjh
     * @date: 2021/9/26 16:09
     */
    @PostMapping("/sendConfirmMessage")
    public String sendConfirmMessage() {
        for (int i = 1; i <= 2; i++) {
            String messageId = String.valueOf(UUID.randomUUID());
            String messageData = "message: sendConfirmMessage ";
            String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            Map<String, Object> map = new HashMap<>();
            map.put("messageId", messageId);
            map.put("messageData", messageData);
            map.put("createTime", createTime);
            rabbitTemplate.convertAndSend("confirm_queue", map, new CorrelationData("" + System.currentTimeMillis()));
        }
        return "ok";
    }


    /**
     * @description: 首部交换机队列
     * @return: java.lang.String
     * @author: xjh
     * @date: 2021/9/26 16:09
     */
    @PostMapping("/sendHeadersMessage")
    public String sendHeadersMessage() {
        for (int i = 1; i <= 2; i++) {
            String messageId = String.valueOf(UUID.randomUUID());
            //配置消息规则
            MessageProperties messageProperties = new MessageProperties();
            messageProperties.setHeader("headers1", "value1");
            messageProperties.setHeader("headers2", "value2");
            String result = "我是首部交换机第" + i + "条队列";
            //要发送的消息，第一个参数为具体的消息字节数组，第二个参数为消息规则
            Message msg = new Message(result.getBytes(), messageProperties);
            rabbitTemplate.convertAndSend("HEADERS_EXCHANGE","", msg);
        }
        return "ok";
    }
}
