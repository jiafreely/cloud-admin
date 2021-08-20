package com.cloud.service.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: ActionTrackInterceptor
 * @description: springboot中RestTemplate设置通用header
 * @date 2021/8/20 9:49
 */
@Component
public class ActionTrackInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        HttpHeaders headers = request.getHeaders();
        // 自定义header头
        headers.add("SYS", "cloud_consumer");
        System.out.println(headers.get("SYS"));
        ClientHttpResponse response  = execution.execute(request, body);
        HttpHeaders responseHeaders = response.getHeaders();
        System.out.println(responseHeaders.getFirst("JF_SN"));
        return response;
    }
}
