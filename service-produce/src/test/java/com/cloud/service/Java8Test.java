package com.cloud.service;

import com.cloud.service.entity.Jdk8Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: Java8Test
 * @description: TODO
 * @date 2021/10/29 17:01
 */
public class Java8Test {
    public static void main(String[] args) {

        List<Jdk8Entity> jdk8EntityList = new ArrayList<>();
        jdk8EntityList.add(new Jdk8Entity().setId("4").setName("张三1").setDateTime("16:00:43"));
        jdk8EntityList.add(new Jdk8Entity().setId("2").setName("张三2").setDateTime("16:00:41"));
        jdk8EntityList.add(new Jdk8Entity().setId("1").setName("张三3").setDateTime("16:00:32"));
        jdk8EntityList.add(new Jdk8Entity().setId("0").setName("张三0").setDateTime("16:00:55"));
        jdk8EntityList.add(new Jdk8Entity().setId("5").setName("张三4").setDateTime("16:00:23"));
        jdk8EntityList.add(new Jdk8Entity().setId("6").setName("张三5").setDateTime("17:00:43"));
        jdk8EntityList.add(new Jdk8Entity().setId("3").setName("张三6").setDateTime("08:00:43"));
        jdk8EntityList.forEach(System.out::println);
        System.out.println("---------------------------------------");
       jdk8EntityList.stream().sorted((a, b) -> a.getDateTime().compareTo(b.getDateTime())).forEach(System.out::println);

        //stringList.stream().filter(s -> !s.equals("ccc")).forEach(System.out::println);
        //
        //stringList.stream().map(String::toUpperCase).sorted((a,b)->b.compareTo(a)).forEach(System.out::println);

    }
}
