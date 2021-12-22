package com.cloud.service.thread.order;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: JoinTest
 * @description: TODO
 * @date 2021/12/21 10:00
 */
public class JoinTest {
    static double year;

    public static void main(String[] args) {
        Thread threadA = new Thread(() -> {
            System.out.println("threadA执行");
            for (year = 0.5; year <= 5; year += 0.5) {
                System.out.println("开始练习唱跳rap：已练习" + year + "年");
                try {
                    Thread.sleep(288);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //众所周知，练习两年半即可出道
                if (year == 2.5) {
                    System.out.println("===========================>练习时长两年半，出道！！！");
                    //留意下这个break，想想如果不break会怎样
                    break;
                }
            }
        });

        Thread threadB = new Thread(() -> {
            try {
                System.out.println("threadB执行");
                // 让threadA线程插队，threadB执行到这儿时会被阻塞，直到threadA执行完
                threadA.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("开始练习打篮球");
        });
        threadA.start();
        threadB.start();
    }
}
