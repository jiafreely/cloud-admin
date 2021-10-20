package com.cloud.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @description: ServiceGateWayApplication
 * @param: null
 * @return:
 * @author: xjh
 * @date: 2021/10/20 10:30
 */
@Slf4j
@EnableDiscoveryClient
@SpringBootApplication
public class ServiceGateWayApplication {
    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext context = SpringApplication.run(ServiceGateWayApplication.class, args);
        Environment environment = context.getBean(Environment.class);
        //environment.getProperty("server.servlet.context-path") 应用的上下文路径，也可以称为项目路径
        String path = environment.getProperty("server.servlet.context-path");
        if (StringUtils.isEmpty(path)) {
            path = "";
        }
        log.info("\n项目地址：http://{}:{}{}", InetAddress.getLocalHost().getHostAddress(), environment.getProperty("server.port"), path);
    }
}
