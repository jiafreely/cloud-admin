package com.cloud.service.controller;

import com.cloud.service.annotation.Log;
import com.cloud.service.result.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: ThreadController
 * @description:
 * @date 2021/9/18 11:32
 */
@Slf4j
@RestController
@Api(description = "多线程")
@RequestMapping("/admin/produce/thread")
public class ThreadController {
    @Autowired
    private ThreadPoolTaskExecutor poolTaskExecutor;

    /**
     * countDownLatch是一个计数器，线程完成一个记录一个，计数器递减，只能只用一次
     */
    CountDownLatch countDownLatch = new CountDownLatch(2);

    @ApiOperation(value = "多线程执行")
    @GetMapping("threadRun")
    @Log(title = "多线程执行任务")
    public R threadRun(@ApiParam(value = "线程休眠时间", required = true) @RequestParam(value = "time") Integer time) {
        try {
            poolTaskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        //// TODO: 2021/9/18  执行业务代码
                        log.info(Thread.currentThread().getName() + "正在执行");
                    } catch (Exception e) {
                        log.info(Thread.currentThread().getName() + ":" + e.getMessage());
                    } finally {
                        countDownLatch.countDown();
                    }
                }
            });

            Future<String> submit = poolTaskExecutor.submit(new Callable<String>() {
                @Override
                public String call() {
                    try {
                        //// TODO: 2021/9/18  执行业务代码
                        return Thread.currentThread().getName() + "callAble执行成功";
                    } catch (Exception e) {
                        log.info(Thread.currentThread().getName() + ":" + e.getMessage());
                        return Thread.currentThread().getName() + "callAble执行失败";
                    } finally {
                        countDownLatch.countDown();
                    }
                }
            });
            log.info(submit.get());

            //模拟线程处理业务逻辑
            Thread.sleep(time * 1000);
            /**
             *countDownLatch的值不为0时会处于一直等待状态无法执行下一步操作
             *等待检查
             */
            countDownLatch.await();
            log.info("业务逻辑执行完毕---------------------------");

            return R.ok();
        } catch (Exception e) {
            log.info(e.getMessage());
            return R.error().message(e.getMessage());
        }
    }
}
