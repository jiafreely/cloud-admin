//package com.cloud.service.feign.fallback;
//
//import com.cloud.service.feign.ProduceService;
//import com.cloud.service.result.R;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
///**
// * @author xjh
// * @version 1.0
// * @ClassName: ProduceServiceFallBack
// * @description: TODO
// * @date 2021/8/18 16:20
// */
//
//@Slf4j
//@Service
//public class ProduceServiceFallBack implements ProduceService {
//    @Override
//    public R testFeign() {
//        log.error("熔断保护");
//        return R.error();
//    }
//
//    @Override
//    public R updateById(String id) {
//        log.error("熔断保护");
//        return R.error();
//    }
//}
