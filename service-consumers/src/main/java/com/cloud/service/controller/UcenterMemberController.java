package com.cloud.service.controller;


import cn.hutool.core.lang.UUID;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloud.service.entity.UcenterMember;
import com.cloud.service.feign.ProduceService;
import com.cloud.service.result.R;
import com.cloud.service.service.UcenterMemberService;
import com.cloud.service.util.RandomUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author xjh
 * @since 2021-08-17
 */
@Slf4j
@Api(description = "个人")
@RestController
@RequestMapping("admin/consumers/ucenter-member")
public class UcenterMemberController {
    private static final String restUrl = "http://10.111.26.200:8110/admin/produce/teacher/serviceProviderByRestTemplate";
    private static final String httpPostUrl = "http://10.111.26.200:8110/admin/produce/teacher/serviceProviderByHttpPost";
    private static final String httpGetUrl = "http://10.111.26.200:8110/admin/produce/teacher/serviceProviderByHttpGet";
    @Autowired
    private UcenterMemberService ucenterMemberService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ProduceService produceService;

    @ApiOperation(value = "HttpGet服务消费者")
    @GetMapping("serviceConsumersByHttpGet")
    public R serviceConsumersByHttpGet() {
        String url = "http://10.111.26.200:8110/admin/produce/teacher/serviceProvider/111/ByHttpGet";
        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("key1", "value1");
        stringObjectMap.put("key2", "value2");
        //stringObjectMap.put("id", "123123123");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key3", "value3");
        jsonObject.put("key4", "value4");
        //jsonObject.put("id", "123123123");

        HttpResponse response = HttpRequest.get(url)
                .header("X-Tenant-ID", "tenantId123")
                .header("X-Request-ID", "Request-ID123")
                .form(stringObjectMap)
                .body(jsonObject.toJSONString())
                .execute();

        return R.ok().data("list", response.body());
    }

    @ApiOperation(value = "HttpPost服务消费者")
    @GetMapping("serviceConsumersByHttpPost")
    public R serviceConsumersByHttpPost() {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key1", "value1");
        jsonObject.put("key2", "value2");
        log.info(jsonObject.toJSONString());

        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("key1", "value1");
        stringObjectMap.put("key2", "value2");
        log.info(String.valueOf(stringObjectMap));


        HttpResponse response = HttpRequest.post(httpPostUrl)
                .header("X-Tenant-ID", "tenantId123")
                .header("X-Request-ID", "Request-ID123")
                .form(stringObjectMap)
                //.body(jsonObject.toJSONString())
                .execute();
        return R.ok().data("list", response.body());
    }

    @ApiOperation(value = "HttpPut服务消费者")
    @GetMapping("serviceConsumersByHttpPut")
    public R serviceConsumersByHttpPut() {
        String url = "http://10.111.26.200:8110/admin/produce/teacher/serviceProvider/111/ByHttpPut";
        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("key1", "value1");
        stringObjectMap.put("key2", "value2");
        //stringObjectMap.put("id", "123123123");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key1", "value1");
        jsonObject.put("key2", "value2");
        //jsonObject.put("id", "123123123");

        HttpResponse response = HttpRequest.put(url)
                .header("X-Tenant-ID", "tenantId123")
                .header("X-Request-ID", "Request-ID123")
                .form(stringObjectMap)
                //.body(jsonObject.toJSONString())
                .execute();

        return R.ok().data("list", response.body());
    }

    @ApiOperation(value = "HttpDelete服务消费者")
    @GetMapping("serviceConsumersByHttpDelete")
    public R serviceConsumersByHttpDelete() {
        String url = "http://10.111.26.200:8110/admin/produce/teacher/serviceProvider/111/ByHttpDelete";
        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("key1", "value1");
        stringObjectMap.put("key2", "value2");
        //stringObjectMap.put("id", "123123123");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key1", "value1");
        jsonObject.put("key2", "value2");
        //jsonObject.put("id", "123123123");

        HttpResponse response = HttpRequest.delete(url)
                .header("X-Tenant-ID", "tenantId123")
                .header("X-Request-ID", "Request-ID123")
                .form(stringObjectMap)
                //.body(jsonObject.toJSONString())
                .execute();

        return R.ok().data("list", response.body());
    }

    @ApiOperation(value = "RestTemplate服务消费者")
    @GetMapping("serviceConsumersByRestTemplate")
    public R serviceConsumersByRestTemplate() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("JF_RN", UUID.fastUUID().toString().replaceAll("-", ""));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<String> exchange = restTemplate.exchange(restUrl, HttpMethod.GET, entity, String.class);
        List<String> jfSn = exchange.getHeaders().get("res");
        String res = exchange.getHeaders().getFirst("res");
        System.out.println("res:" + res);
        R r = new R();
        //R r = restTemplate.getForObject(restUrl, R.class);
        //System.out.println("---------------------------------数据结果:" + r.getData());
        return R.ok().data("list", r);
    }

    @ApiOperation(value = "feign服务消费者")
    @GetMapping("serviceConsumersByFeign")
    public R serviceConsumersByFeign() {
        R r = produceService.testFeign();
        return r;
    }

    @ApiOperation(value = "查询教师:分页")
    @GetMapping("queryPageList")
    public R queryPageList() {
        Page<UcenterMember> page = new Page<>(1, 2);
        IPage<UcenterMember> ucenterMemberIPage = ucenterMemberService.page(page);
        return R.ok().data("list", ucenterMemberIPage.getRecords());
    }

    @ApiOperation(value = "添加数据")
    @PostMapping("add")
    //@GlobalTransactional(rollbackFor = Exception.class)
    public R add() throws Exception {
        Boolean save = ucenterMemberService.UcenterAdd();
        if (1 == 1) {
            throw new Exception("制造异常");
        }
        return R.ok().data("code", save);
    }

    @ApiOperation(value = "修改数据")
    @PostMapping("updateById")
    public R updateById(@ApiParam(value = "个人id", required = true) @RequestParam String id) {
        UcenterMember ucenterMember = ucenterMemberService.getById(id);
        ucenterMember.setId(id);
        ucenterMember.setNickname(RandomUtils.getSixBitRandom());
        R r = restTemplate.getForObject(restUrl, R.class);
        produceService.updateById("1426565602478309378");
        ucenterMemberService.updateById(ucenterMember);
        return R.ok().data("code", "success");
    }
}

