package com.cloud.service.feign;

import com.cloud.service.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Service
@FeignClient(value = "service-produce")
public interface ProduceService {
    @GetMapping("/admin/produce/teacher/serviceProviderByRestTemplate")
    R testFeign();

    @GetMapping("/admin/produce/teacher/updateById")
    R updateById(String id);

    @PostMapping("/admin/produce/teacher/add")
    R add();
}
