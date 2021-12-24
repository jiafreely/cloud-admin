package com.cloud.service.controller.sentinel.demo;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: SentinelTestController
 * @description: TODO
 * @date 2021/12/24 14:56
 */
@Slf4j
@Api(description = "sentinelTest")
@RestController
@RequestMapping("admin/consumers/sentinelTest")
public class SentinelTestController {

    //流控
    @GetMapping("/testSentinel")
    public String testSentinel() {
        return "success";
    }

    //热点参数限流规则
    //触发限流策略，就会进入当前类的handHotKey方法，或者配置blockHandlerClass，
    // 就会进入blockHandlerClass所配置类中的handHotKey方法
    @GetMapping("/hotKeySentinel")
    @SentinelResource(value = "hotKeySentinel", blockHandler = "handHotKey")
    public String hotKeySentinel(@RequestParam(value = "a", required = false) String a,
                                 @RequestParam(value = "b", required = false) String b) {
        return a + "\t" + b;
    }


    public String handHotKey(String a1, String a2, BlockException e) {
        return "热点数据限流";
    }
}
