package com.cloud.service.feign.fallback;

import com.cloud.service.feign.CrawlingService;
import com.cloud.service.result.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: XiaoKServiceFallBack
 * @description: TODO
 * @date 2021/10/20 10:49
 */
@Slf4j
@Service
public class CrawlingServiceFallBackImpl implements CrawlingService {
    @Override
    public R addJsoup() {
        log.error("熔断保护");
        return R.error();
    }

    @Override
    public R showToDayList() {
        log.error("熔断保护");
        return R.error();
    }
}
