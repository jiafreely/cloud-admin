package com.cloud.service.receiver.config;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: MyAckReceiver
 * todo 如果在yml文件中加入ack确认是否所有队列均已开启
 * url https://blog.csdn.net/qq_35387940/article/details/100514134
 * url https://blog.csdn.net/weixin_43732955/article/details/108969264
 * @description: 对应的手动确认消息监听类，MyAckReceiver.java（手动确认模式需要实现 ChannelAwareMessageListener）：
 * 之前的相关监听器可以先注释掉，以免造成多个同类型监听器都监听同一个队列
 * 手动ack：当正确消费消息后，调用channel.basicAck()，进行手动确认；如果在处理消息过程中发送异常，
 * 可以调用channel.basicNack，表示拒绝消息，并且通过参数设置，可以让没有消费的消息再次回到队列，
 * 下次消费（但是不断的重入队列，虽然消息不会丢失、却会造成消息队列阻塞、可以采用重试机制，
 * 或者转移到另外的队列如：死信队列中）。 这样的话，控制了消息处理过程中的正确性。
 * @date 2021/9/26 10:58
 */
@Component
public class MyAckReceiver implements ChannelAwareMessageListener {
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            //因为传递消息的时候用的map传递,所以将Map从Message内取出需要做些处理
            String msg = message.toString();
            String[] msgArray = msg.split("'");//可以点进Message里面看源码,单引号直接的数据就是我们的map消息数据
            Map<String, String> msgMap = mapStringToMap(msgArray[1].trim(), 3);
            String messageId = msgMap.get("messageId");
            String messageData = msgMap.get("messageData");
            String createTime = msgMap.get("createTime");

            if ("TestDirectQueue".equals(message.getMessageProperties().getConsumerQueue())) {
                System.out.println("消费的消息来自的队列名为：" + message.getMessageProperties().getConsumerQueue());
                System.out.println("消息成功消费到  messageId:" + messageId + "  messageData:" + messageData + "  createTime:" + createTime);
                System.out.println("执行TestDirectQueue中的消息的业务处理流程......");
            }

            if ("fanout.A".equals(message.getMessageProperties().getConsumerQueue())) {
                System.out.println("消费的消息来自的队列名为：" + message.getMessageProperties().getConsumerQueue());
                System.out.println("消息成功消费到  messageId:" + messageId + "  messageData:" + messageData + "  createTime:" + createTime);
                System.out.println("执行fanout.A中的消息的业务处理流程......");
            }
            channel.basicAck(deliveryTag, true); //第二个参数，手动确认可以被批处理，当该参数为 true 时，则可以一次性确认 delivery_tag 小于等于传入值的所有消息
//			channel.basicReject(deliveryTag, true);//第二个参数，true会重新放回队列，所以需要自己根据业务逻辑判断什么时候使用拒绝
        } catch (Exception e) {
            /**
             * @param long deliveryTag 消息的标志
             * @param boolean multiple 是否批量处理
             * @param boolean requeue  是否重入队列
             */
            //消息的标志,是否批量处理,是否重入队列
            channel.basicNack(deliveryTag, false, false);
            e.printStackTrace();
        }
    }

    //{key=value,key=value,key=value} 格式转换成map
    private Map<String, String> mapStringToMap(String str, int entryNum) {
        str = str.substring(1, str.length() - 1);
        String[] strs = str.split(",", entryNum);
        Map<String, String> map = new HashMap<String, String>();
        for (String string : strs) {
            String key = string.split("=")[0].trim();
            String value = string.split("=")[1];
            map.put(key, value);
        }
        return map;
    }
}
