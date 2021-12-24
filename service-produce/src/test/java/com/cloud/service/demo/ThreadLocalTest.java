package com.cloud.service.demo;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: ThreadLocalTest
 * @description: TODO
 * @date 2021/12/23 15:33
 */
public class ThreadLocalTest {
    public static void main(String[] args) {
        //InheritableThreadLocal 可以使不同线程共享变量
        ThreadLocal threadLocal = new InheritableThreadLocal();
        threadLocal.set("主线程中的数据");
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "获取了" + threadLocal.get());
        }).start();
    }
}
