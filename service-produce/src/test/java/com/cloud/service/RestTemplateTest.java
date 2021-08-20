package com.cloud.service;

import com.cloud.service.result.R;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: RestTemplateTest
 * @description: TODO
 * @date 2021/8/20 10:11
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceProduceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestTemplateTest {
    private static final String restUrl = "http://10.111.26.200:8110/admin/produce/teacher/serviceProviderByRestTemplate";
    @Autowired
    private TestRestTemplate testrestTemplate;

    @Test
    public void testRestTemplate() {
        R r = testrestTemplate.getForObject(restUrl, R.class);
        System.out.println(r);
    }
}
