package com.cloud.service.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: WebUtils
 * @description: TODO
 * @date 2021/8/18 10:58
 */
@Slf4j
public class WebUtils extends org.springframework.web.util.WebUtils{
    /**
     * 把HttpServletRequest中的Header全部提取出来，按“名称-值”对放进一个HashMap中。如果值有多个会被转成数组。
     *
     * @param request HttpServletRequest兼容实例
     * @return {@code Map<String, Object}
     */
    public static Map<String, Object> getHeaders(HttpServletRequest request) {
        Map<String, Object> headers = new HashMap<String, Object>(0);
        Enumeration<String> names = request.getHeaderNames();
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            Enumeration<String> values = request.getHeaders(name);
            List<Object> valueList = new ArrayList<Object>(0);
            while (values.hasMoreElements()) {
                Object value = (Object) values.nextElement();
                valueList.add(value);
            }
            if (valueList.size() > 0) {
                if (valueList.size() == 1) {
                    headers.put(name, valueList.get(0));
                } else {
                    headers.put(name, valueList.toArray());
                }
            }
        }
        return headers;
    }

    public static void forceResponseNoCache(HttpServletResponse response) {
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache,max-age=0");
        response.setDateHeader("Expires", 0);
    }

    /**
     * 从当前HTTP请求中识别客户端IP地址。相对于服务端来说，“远程地址”就是指客户端的地址。
     * <p>
     * 除了访问CGI基本属性REMOTE_ADDR，还可能会根据HTTP Header中的特殊属性来识别客户端的“真实”地址。
     *
     * @param request HTTP请求上下文
     * @param tryHeaders 不仅仅只获取远程地址，也尝试从Header中查找常见转发、代理会使用的客户端IP标识
     * @return 客户端地址。如果没有传入request则直接返回UNK
     */
    public static String determineRemoteIp(HttpServletRequest request, boolean tryHeaders) {
        if (request == null) {
            return "UNK";  //未知
        }
        String clientIp = request.getRemoteAddr();
        if (tryHeaders) {
            if (StringUtils.isNotEmpty(request.getHeader("$WSRH"))) {
                // 这是使用WebSphere PlugIn转发时才会使用的特殊Header名
                clientIp = request.getHeader("$WSRH");
                // 以下是一些常见HTTP代理(反向代理)转发时使用的Header名
            } else if (StringUtils.isNotEmpty(request.getHeader("X-FORWARDED-FOR"))) {
                clientIp = request.getHeader("X-FORWARDED-FOR");
            } else if (StringUtils.isNotEmpty(request.getHeader("X-REAL-IP"))) {
                clientIp = request.getHeader("X-REAL-IP");
            } else if (StringUtils.isNotEmpty(request.getHeader("HTTP_CLIENT_IP"))) {
                clientIp = request.getHeader("HTTP_CLIENT_IP");
            } else if (StringUtils.isNotEmpty(request.getHeader("HTTP_X_FORWARDED_FOR"))) {
                clientIp = request.getHeader("HTTP_X_FORWARDED_FOR");
            } else if (StringUtils.isNotEmpty(request.getHeader("HTTP_X_FORWARDED"))) {
                clientIp = request.getHeader("HTTP_X_FORWARDED");
            } else if (StringUtils.isNotEmpty(request.getHeader("HTTP_FORWARDED_FOR"))) {
                clientIp = request.getHeader("HTTP_FORWARDED_FOR");
            } else if (StringUtils.isNotEmpty(request.getHeader("HTTP_FORWARDED"))) {
                clientIp = request.getHeader("HTTP_FORWARDED");
            }
        }

        return clientIp;
    }

    /**
     * 判断是否AJAX请求。判断是依据x-requested-with头是否为XMLHttpRequest。
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String requestedWith = request.getHeader("X-Requested-With");
        if ("XMLHttpRequest".equalsIgnoreCase(requestedWith)) {
            return true;
        } else {
            return false;
        }
    }
}
