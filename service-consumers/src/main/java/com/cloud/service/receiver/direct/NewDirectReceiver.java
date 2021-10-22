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
 * @description: 有多个消费默认采取轮训消费
 * 注意：
 * 重试并不是RabbitMQ重新发送了消息，仅仅是消费者内部进行的重试，换句话说就是重试跟mq没有任何关系；
 * 因此上述消费者代码不能添加try{}catch(){}，一旦捕获了异常，在自动ack模式下，就相当于消息正确处理了，
 * 消息直接被确认掉了，不会触发重试的；
 *
 * 防止消息重复消费
 * 防止消息重复消费就是保证接口的幂等性，也就是保证相同的数据多次请求同一接口结果仍然一致。
 * 比较通用的做法是给消息设置一个全局性唯一性Id（可根据Snowflake算法实现）存储在redis中，请求接口前先查询redis判断再执行后续操作。
 * 根据具体的业务进行分析，例如订单扣款，请求接口前先根据订单表的是否支付相关字段进行判断等等
 * @date 2021/9/23 16:56
 */

@Component
@RabbitListener(queues = "TestDirectQueue")//监听的队列名称 TestDirectQueue
public class NewDirectReceiver {
    @RabbitHandler
    public void process(Map testMessage, Channel channel, Message message) throws IOException {
        //拿到消息延迟消费
        try {
            Thread.sleep(1000 * 3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            //当我们对消息者二进行限制等于8时，不接受消息队列传递来的消息时，消息队列会随机重发那条消息，
            // 直至消息发送至完好的消费者一时，才会把消息消费掉,前提是没有设置死信队列

            //如果设置了死信队则会将丢失的消息传到死信队列中进行处理,不会进行重试
            if ("8".equals(testMessage.get("messageData"))) {
                System.out.println("messageData == 8");
                ////消息的标志,是否批量处理,是否重入队列
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
                System.err.println("第二个-----DirectReceiver消费者收到消息失败  : " + testMessage.toString());
            } else {
                //手动确认
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                System.out.println("第二个-----DirectReceiver消费者收到消息成功  : " + testMessage.toString());
            }
        } catch (Exception e) {
            //消息的标志,是否批量处理,是否重入队列
            //消费者处理出了问题，需要告诉队列信息消费失败
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            System.err.println("第二个-----DirectReceiver消费者收到消息失败  : " + testMessage.toString());
        }
    }
}
