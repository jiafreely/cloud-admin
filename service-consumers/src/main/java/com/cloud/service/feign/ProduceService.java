package com.cloud.service.feign;

import com.cloud.service.feign.fallback.ProduceServiceFallBack;
import com.cloud.service.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
@FeignClient(value = "service-produce",fallback = ProduceServiceFallBack.class)
public interface ProduceService {
    @GetMapping("/admin/produce/teacher/serviceProviderByRestTemplate")
    R testFeign();

    @GetMapping("/admin/produce/teacher/updateById")
    R updateById(String id);
}
