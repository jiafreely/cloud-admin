package com.cloud.service.aspectj;

import com.alibaba.fastjson.JSON;
import com.cloud.service.annotation.Log;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: LogAspect
 * @description: 操作日志记录处理
 * @date 2021/9/18 17:04
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    /**
     * @description: 配置织入点 --注解的方式
     * @date: 2021/9/18 17:14
     */
/*    @Pointcut("@annotation(com.cloud.service.annotation.Log)")
    public void logPointCut() {

    }*/

    /**
     * @description: 配置织入点 --路径的方式
     * execution() 表达式的主体
     * 第一个"*"符号 表示返回值的类型任意
     * com.cloud.service AOP所切的服务的包名，即，需要进行横切的业务类
     * 包名后面的".." 表示当前包及子包
     * 第二个"*" 表示类名，*即所有类
     * .*(..) 表示任何方法名，括号表示参数，两个点表示任何参数类型
     * @param:
     * @return: void
     * @author: xjh
     * @date: 2021/9/22 15:20
     */
    @Pointcut("execution(* com.cloud.service.controller..*(..))")
    public void logPointCut() {

    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private Log getAnnotationLog(JoinPoint joinPoint) throws Exception {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null) {
            return method.getAnnotation(Log.class);
        }
        return null;
    }

    /**
     * @description: 前置通知
     * @param:
     * @return: void
     * @author: xjh
     * @date: 2021/9/22 15:40
     */
/*    @Before("logPointCut()")
    private void doBefore() {
        System.out.println("执行前置通知切面--------------------------");
    }*/

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
/*    @After("logPointCut()")
    public void doAfterReturning(JoinPoint joinPoint) {
        try {
            System.out.println("执行后置通知切面--------------------------");
            // 接收到请求，记录请求内容
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            String rquestUrl = request.getRequestURL().toString();

            //请求的类名
            String className = joinPoint.getTarget().getClass().getName();

            //请求的方法名
            Signature signature = joinPoint.getSignature();
            String methodName = signature.getName();

            //日志注解注入的信息
            Log annotationLog = getAnnotationLog(joinPoint);
            if (!ObjectUtil.isEmpty(annotationLog)) {
            }
            // todo 保存数据库
            log.info(rquestUrl + "\t" + className + "\t" + methodName + "\t" + annotationLog);
        } catch (Exception e) {
            // 记录本地异常日志
            log.error("==后置通知异常==");
            log.error("异常信息:{}", e.getMessage());
            e.printStackTrace();
        }
    }*/


    /**
     * 环绕增强：目标方法执行前后分别执行一些代码，发生异常的时候执行另外一些代码
     *
     * @return
     */
    @Around("logPointCut()")
    public Object aroundMethod(ProceedingJoinPoint jp) {
        LocalDateTime startTime = LocalDateTime.now();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Signature signature = jp.getSignature();
        Object result = null;
        try {
            //业务逻辑执行前执行
            log.info("执行开始时间----------{}", startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            //log.info("【环绕增强中的--->前置增强】：the method 【" + methodName + "】 begins with " + Arrays.asList(jp.getArgs()));
            //执行目标方法
            result = jp.proceed();
            //业务逻辑执行完毕后执行
            //log.info("【环绕增强中的--->返回增强】：the method 【" + methodName + "】 ends with " + result);
        } catch (Throwable e) {
            result = "error";
            log.info(signature.getDeclaringTypeName() + "." + signature.getName() + "occurs exception:{}", e);
        }
        //log.info("【环绕增强中的--->后置增强】：-----------------end----------------------");

        //System.out.println(request.getQueryString().toString());//获取请求的参数
        Map<String, String> resMap = new HashMap<String, String>();
        Enumeration<?> temp = request.getParameterNames();
        if (null != temp) {
            while (temp.hasMoreElements()) {
                String en = (String) temp.nextElement();
                String value = request.getParameter(en);
                resMap.put(en, value);
                //如果字段的值为空，判断若值为空，则删除这个字段
           /*     if (StringUtils.isEmpty(en)) {
                    resMap.remove(en);
                }*/
            }
        }
        log.info("Log Infomation {URL:{}\tmethodType:{}\tmethod:{}\tIP:{}\targs:{}\tresult:{}}",
                request.getRequestURI(),
                request.getMethod(),
                signature.getDeclaringTypeName() + "." + signature.getName(),
                request.getRemoteAddr(),
                JSON.toJSONString(resMap),
                result);

        LocalDateTime endTime = LocalDateTime.now();
        //log.info("执行结束时间:----------{}", endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        log.info("执行方法使用时间:----------{}s", startTime.until(endTime, ChronoUnit.SECONDS));
        //todo 插入数据库
        return result;
    }

}
