package com.cloud.service.thread.order;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: VolatileTest
 * @description: TODO
 * @date 2021/12/21 10:08
 */
public class VolatileTest {
    //定义一个共享变量用来线程间通信，注意用volatile修饰，保证它内存可见
    static volatile boolean flag = false;
    static double year;

    public static void main(String[] args) {
        Thread threadA = new Thread(() -> {
            while (true) {
                if (!flag) {
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
                            // 通知threadB你可以执行了
                            flag = true;
                            //同样留意这个break
                            break;
                        }
                    }
                    break;
                }
            }
        });
        //线程B，练习打篮球
        Thread threadB = new Thread(() -> {
            while (true) {
                // 监听flag
                if (flag) {
                    System.out.println("开始练习打篮球");
                    break;
                }
            }
        });
        threadA.start();
        threadB.start();
    }
}
