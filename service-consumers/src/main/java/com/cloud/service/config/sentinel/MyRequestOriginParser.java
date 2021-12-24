package com.cloud.service.config.sentinel;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: MyRequestOriginParser
 * @description: Sentinel系统设置(授权规则)
 * 白名单，则流控应用中设置的这些可以访问，如果是黑名单则流控应用中设置的不能访问
 * 通过设置后，所有请求必须带上参数origin=XXX，以上面的配置为例，只有origin=xjh的请求才能通过访问。
 * 如果不喜欢参数的方式，可以在代码中换成header传递，效果一样
 * @date 2021/12/24 16:01
 */
@Slf4j
@Component
public class MyRequestOriginParser implements RequestOriginParser {
    @Override
    public String parseOrigin(HttpServletRequest httpServletRequest) {
        log.info("sentinel 授权设置");
        String origin = httpServletRequest.getParameter("origin");
      /*  if (origin == null) {
            throw new IllegalArgumentException("origin参数未指定");
        }*/
        return origin;
    }
}
