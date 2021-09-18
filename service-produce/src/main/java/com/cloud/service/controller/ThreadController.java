package com.cloud.service.controller;

import com.cloud.service.result.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: ThreadController
 * @description: TODO
 * @date 2021/9/18 11:32
 */
@Slf4j
@RestController
@Api(description = "多线程")
@RequestMapping("/admin/produce/thread")
public class ThreadController {
    @Autowired
    private ThreadPoolTaskExecutor poolTaskExecutor;

    @ApiOperation(value = "多线程执行")
    @GetMapping("threadRun")
    public R threadRun() {
        try {
            poolTaskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    log.info(Thread.currentThread().getName() + "正在执行");
                }
            });
            Future<String> submit = poolTaskExecutor.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return Thread.currentThread().getName()+"callAble执行成功";
                }
            });
            log.info(submit.get());
            return R.ok();
        } catch (Exception e) {
            log.info(e.getMessage());
            return R.error().message(e.getMessage());
        }
    }
}
