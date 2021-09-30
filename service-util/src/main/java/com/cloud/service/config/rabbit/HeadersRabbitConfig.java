package com.cloud.service.config.rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: HeadersRabbitConfig
 * @description: 首部交换机
 * todo 首部交换机是忽略routing_key的一种路由方式。路由器和交换机路由的规则是通过Headers信息来交换的，
 * todo 这个有点像HTTP请求中的请求头。将一个交换机声明成首部交换机，绑定一个队列的时候，定义一个Hash的数据结构，
 * todo 消息发送的时候，会携带一组hash数据结构的信息，当Hash内容匹配上的时候，消息就会被写入队列。
 * todo 绑定交换机和队列的时候，Hash结构中要求携带一个键"x-match"，这个键的Value可以是any或者all，
 * todo 这代表消息携带的Hash是需要全部匹配(all)，还是仅仅匹配一个键(any)就可以了。相比较直连交换机，
 * todo 首部交换机的优势是匹配的规则不被限定为字符串(string)
 * @date 2021/9/28 9:46
 */
@Configuration
public class HeadersRabbitConfig {
    @Bean
    public Queue headersQueue() {
        return new Queue("HEADERS_QUEUE");
    }

    @Bean
    public HeadersExchange headersExchange() {
        return new HeadersExchange("HEADERS_EXCHANGE");
    }

    //将headersQueue与HeadersExchange交换机绑定
    @Bean
    public Binding bingHeadersQueue() {
        //map为绑定的规则
        Map<String, Object> map = new HashMap<>();
        map.put("headers1", "value1");
        map.put("headers2", "value2");
        //whereAll表示需要满足所有条件
        return BindingBuilder.bind(headersQueue()).to(headersExchange()).whereAll(map).match();
    }
}
