package com.cloud.service.scheduled.demo.insertDB;

import com.cloud.service.entity.demo.insertDB.Person;
import com.cloud.service.service.demo.insertDB.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: insertPerSonScheduled
 * @description: *1、想要拉高插入效率，肯定不能够一条一条插了，必须得foreach批量插入，经测试，单次批量3w条以下时性价比最高，并且不用修改mysql配置
 * 2、文章开头说了，得开多个线程异步插入，我们先把应用层效率拉满，mysql顶不顶得住
 * 3、我们不可能单次提交一亿次insert，这谁顶得住，而且大量插入操作会很耗时，短时间内完不成，我们不可能一直守着，我的方案是用定时任务
 * 原文链接：https://blog.csdn.net/qq_33709582/article/details/121745749
 * @date 2021/12/24 8:41
 */
//@Component
@Slf4j
public class insertPerSonScheduled {
    private static final int THREAD_COUNT = 6;
    @Autowired
    private PersonService personService;
    @Autowired
    private ThreadPoolTaskExecutor poolTaskExecutor;
    private AtomicInteger integer = new AtomicInteger();
    private Random random = new Random();
    private String[] names = {"黄某人", "负债程序猿", "谭sir", "郭德纲", "蔡徐鸡", "蔡徐母鸡", "李狗蛋", "铁蛋", "赵铁柱"};
    private String[] addrs = {"二仙桥", "成华大道", "春熙路", "锦里", "宽窄巷子", "双子塔", "天府大道", "软件园", "熊猫大道", "交子大道"};
    private String[] companys = {"京东", "腾讯", "百度", "小米", "米哈游", "网易", "字节跳动", "美团", "蚂蚁", "完美世界"};

    @Scheduled(cron = "0/15 * * * * ?")
    public void insertList() {
        System.out.println("poolTaskExecutor:"+poolTaskExecutor.getCorePoolSize()+poolTaskExecutor.getMaxPoolSize());
        System.out.println("本轮任务开始，总任务数：" + THREAD_COUNT);
        long start = System.currentTimeMillis();
        AtomicLong end = new AtomicLong();
        for (int i = 0; i < THREAD_COUNT; i++) {
            poolTaskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (int j = 0; j < 20; j++) {
                            personService.saveBatch(getPersonList(5000));
                        }
                        end.set(System.currentTimeMillis());
                        System.out.println("本轮任务耗时：" + (end.get() - start) + "____已执行" + integer.addAndGet(1) + "个任务" + "____当前队列任务数" + poolTaskExecutor.getCorePoolSize());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private ArrayList<Person> getPersonList(int count) {
        ArrayList<Person> persons = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            persons.add(getPerson());
        }
        return persons;
    }

    private Person getPerson() {
        Person person = new Person()
                .setName(names[random.nextInt(names.length)])
                .setPhone(18800000000L + random.nextInt(88888888))
                .setSalary(new BigDecimal(random.nextInt(99999)))
                .setCompany(companys[random.nextInt(companys.length)])
                .setIfSingle(random.nextInt(2))
                .setSex(random.nextInt(2))
                .setAddress("四川省成都市" + addrs[random.nextInt(addrs.length)])
                .setCreateUser(names[random.nextInt(names.length)]);
        return person;
    }
}
