package com.cloud.service;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: ApiTest
 * @description: TODO
 * @date 2021/11/17 9:39
 */
public class ApiTest {
    @Test
    public void test1() {
        try {
            String apiURL = "www.baidu.com" + "/test";
            //url参数
            MultiValueMap<String, String> parametersMap = new LinkedMultiValueMap<>();
            parametersMap.add("biz", "123");
            parametersMap.add("user", "123");
            //request body
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("title", "标题");
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiURL).queryParams(parametersMap);
            HttpResponse response = HttpRequest.post(builder.toUriString())
                    .header("ID", "111")
                    .body(jsonObject.toJSONString())
                    .execute();
            JSONObject result = JSONObject.parseObject(response.body());
        } catch (RestClientException e) {
            e.printStackTrace();

        }
    }
}
