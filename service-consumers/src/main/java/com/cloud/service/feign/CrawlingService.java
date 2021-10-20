package com.cloud.service.feign;

import com.cloud.service.feign.fallback.CrawlingServiceFallBack;
import com.cloud.service.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Service
@FeignClient(value = "service-produce",fallback = CrawlingServiceFallBack.class)
public interface CrawlingService {


    @PostMapping("/admin/xiaok/addJsoup")
    R addJsoup();

    @GetMapping("/admin/xiaok/showToDayList")
    public R showToDayList();
}
