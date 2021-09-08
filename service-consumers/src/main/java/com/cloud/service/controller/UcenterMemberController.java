package com.cloud.service.controller;


import cn.hutool.core.lang.UUID;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
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

import java.util.List;

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
    private static final String httpUrl = "http://10.111.26.200:8110/admin/produce/teacher/serviceProviderByHttp";
    @Autowired
    private UcenterMemberService ucenterMemberService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ProduceService produceService;

    @ApiOperation(value = "Http服务消费者")
    @GetMapping("serviceConsumersByHttp")
    public R serviceConsumersByHttp() {
        HttpRequest request = HttpRequest.get(httpUrl);
        request.header("JF_UR", "123456");
        String rid = UUID.fastUUID().toString().replaceAll("-", "");
        HttpResponse response = request.execute();
        return R.ok().data("list", "");
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

