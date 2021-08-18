package com.cloud.service.config;

import com.cloud.service.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.WebContentGenerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: HttpServletContextAware
 * 支持快捷获取HTTP及Servlet相关上下文的抽象控制器.
 * <p>
 * SpringMVC控制器实现推荐使用RequestMapping注解自定义URI映射到每个方法上。
 *
 * @see org.springframework.web.servlet.mvc.AbstractController AbstractController -
 *      如果要编程式实现Controller兼容方式可以继承它
 * @see org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter
 *      RequestMappingHandlerAdapter - 如果要编程式使用URI路径映射到方法名的方式可以继承它
 * @date 2021/8/18 10:54
 */

@Controller
public abstract class HttpServletContextAware extends WebContentGenerator {
    /** 子类也可以使用的通用Logger */
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 取得当前请求的HttpServletRequest.
     *
     * @return HttpServletRequest
     */
    protected final HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 取得当前请求的HttpServletResponse.
     *
     * @return HttpServletResponse
     */
    protected final HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    /**
     * 取得当前请求的Session.
     *
     * @return HttpSession
     */
    protected final HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * 取得当前请求的HTTP协议首行内容.
     * <p>
     * 并非实际客户端发来的首行，而是从请求上下文中取出HTTP<code>METHOD+URI+PROTOCOL</code>拼接模拟出来的。
     *
     * @return 方法+空格+URI+空格+协议版本
     */
    protected final String getHttpFirstLine() {
        HttpServletRequest req = getRequest();
        return String.format("%s %s %s", req.getMethod(), req.getRequestURI(), req.getProtocol());
    }

    /**
     * 尝试获取当前请求的真实客户端地址.
     *
     * @return 默认从<code>request.getRemoteAddr()</code>，也会尝试从一些常见HTTP Header中识别
     */
    protected String getRealRemoteAddr() {
        return determineRemoteIp(getRequest(), true);
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
        return WebUtils.determineRemoteIp(request,tryHeaders);
    }

    /**
     * 强制令响应成为no cache方式的方便方法.
     * <p>
     * 其实就是向<code>HttpServletResponse</code>中添加Pragma、Cache-Control、Expires这几个Header。
     */
    protected void forceResponseNoCache() {
        WebUtils.forceResponseNoCache(getResponse());
    }

    /**
     * 判断是否AJAX请求。
     * <p>
     * 判断是依据x-requested-with头是否为XMLHttpRequest。
     */
    protected boolean isAjaxRequest() {
        return WebUtils.isAjaxRequest(getRequest());
    }
}
