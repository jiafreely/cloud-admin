package com.cloud.service.filter;

import com.google.gson.JsonObject;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: AuthGlobalFilter
 * @description: TODO
 * @date 2021/10/20 16:03
 */

@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        //校验/admin/consumers/crawling 下的所有url请求必须带token
        AntPathMatcher antPathMatcher = new AntPathMatcher();
  /*      if (antPathMatcher.match("/admin/consumers/crawling/**", path)) {
            List<String> tokenList = request.getHeaders().get("token");

            //没有token
            if (null == tokenList) {
                ServerHttpResponse response = exchange.getResponse();
                return out(response);
            }

            //token校验失败
            Boolean isCheck = "123456".equals(tokenList.get(0));
            if (!isCheck) {
                ServerHttpResponse response = exchange.getResponse();
                return out(response);
            }
        }*/

        //放行
        return chain.filter(exchange);
    }

    //定义当前过滤器的优先级，值越小，优先级越高
    @Override
    public int getOrder() {
        return 0;
    }

    private Mono<Void> out(ServerHttpResponse response) {

        JsonObject message = new JsonObject();
        message.addProperty("success", false);
        message.addProperty("code", 500);
        message.addProperty("data", "");
        message.addProperty("message", "鉴权失败");
        byte[] bytes = message.toString().getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bytes);
        //指定编码，否则在浏览器中会中文乱码
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        //输出http响应
        return response.writeWith(Mono.just(buffer));
    }
}
