package com.cloud.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: ServiceConsumersApplication
 //* @see DataSourceProxyAutoConfiguration 代理数据源类
 * @date 2021/8/17 16:46
 */

@Slf4j
@EnableSwagger2
@EnableFeignClients
@SpringBootApplication
//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
//@Import({DataSourceProxyAutoConfiguration.class})
@EnableDiscoveryClient
public class ServiceConsumersApplication {
    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext context = SpringApplication.run(ServiceConsumersApplication.class, args);
        Environment environment = context.getBean(Environment.class);
        //environment.getProperty("server.servlet.context-path") 应用的上下文路径，也可以称为项目路径
        String path = environment.getProperty("server.servlet.context-path");
        if (StringUtils.isEmpty(path)) {
            path = "";
        }
        log.info("\n项目地址：http://{}:{}{}", InetAddress.getLocalHost().getHostAddress(), environment.getProperty("server.port"), path);
        log.info("\nswagger地址：http://{}:{}{}/swagger-ui.html", InetAddress.getLocalHost().getHostAddress(), environment.getProperty("server.port"), path);
    }
}
