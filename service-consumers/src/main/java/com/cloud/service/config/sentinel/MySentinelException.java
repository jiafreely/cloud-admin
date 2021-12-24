package com.cloud.service.config.sentinel;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: MySentinelException
 * @description: 限流异常自定义返回
 */
@Component
public class MySentinelException implements BlockExceptionHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e) throws Exception {
        JSONObject object = new JSONObject();
        if (e instanceof FlowException) {
            object.put("status", "100");
            object.put("message", "接口限流");
            object.put("data", null);
        } else if (e instanceof DegradeException) {
            object.put("status", "101");
            object.put("message", "服务降级");
            object.put("data", null);
        } else if (e instanceof ParamFlowException) {
            object.put("status", "102");
            object.put("message", "热点参数限流");
            object.put("data", null);
        } else if (e instanceof SystemBlockException) {
            object.put("status", "103");
            object.put("message", "触发系统保护");
            object.put("data", null);
        } else if (e instanceof AuthorityException) {
            object.put("status", "104");
            object.put("message", "授权规则不通过");
            object.put("data", null);
        }

        httpServletResponse.setStatus(500);
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(httpServletResponse.getWriter(), object);
    }
}
