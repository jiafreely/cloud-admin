package com.cloud.service.controller.jsoup;

import com.cloud.service.feign.CrawlingService;
import com.cloud.service.result.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: JsoupController
 * @description: TODO
 * @date 2021/10/20 10:37
 */

@Slf4j
@RestController
@Api(description = "聚合爬取",tags = "聚合爬取")
@RequestMapping("/admin/consumers/crawling")
public class CrawlingController {

    @Autowired
    private CrawlingService crawlingService;

    @ApiOperation(value = "聚合爬取当天数据")
    @PostMapping("addJsoup")
    public R addJsoup() {
        R r = crawlingService.addJsoup();
        return r;
    }

    @ApiOperation(value = "查询今天的数据")
    @GetMapping("showToDayList")
    public R showToDayList() {
        R r = crawlingService.showToDayList();
        return r;
    }
}
