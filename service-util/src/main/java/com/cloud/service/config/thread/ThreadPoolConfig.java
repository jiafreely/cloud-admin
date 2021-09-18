package com.cloud.service.config.thread;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: ThreadPoolConfig
 * @description: url https://www.cnblogs.com/seasail/p/12179401.html
 * https://www.cnblogs.com/lcxdevelop/p/10487857.html
 * 无返回值的任务使用execute(Runnable)
 * 有返回值的任务使用submit(Runnable)
 * @date 2021/9/18 10:38
 */

@Data
@Configuration
@ConfigurationProperties(prefix = "thread") //自定义标识前缀
public class ThreadPoolConfig {
    // 核心线程池大小 如果池中的实际线程数小于corePoolSize,无论是否其中有空闲的线程，都会给新的任务产生新的线程
    private int corePoolSize;

    // 最大可创建的线程数 连接池中保留的最大连接数
    private int maxPoolSize ;

    // 队列最大长度 线程池所使用的缓冲队列
    private int queueCapacity;

    // 线程池维护线程所允许的空闲时间
    private int keepAliveSeconds;

    //线程起名称前缀，便于分析日志
    private String threadNamePrefix;

    @Bean(name = "threadPoolTaskExecutor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor()
    {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setMaxPoolSize(maxPoolSize);
        executor.setCorePoolSize(corePoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.setThreadNamePrefix(threadNamePrefix);
        // 线程池对拒绝任务(无线程可用)的处理策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }
}
