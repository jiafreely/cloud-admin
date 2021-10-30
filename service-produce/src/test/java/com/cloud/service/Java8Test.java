package com.cloud.service;

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


        List<String> stringList = new ArrayList<>();
        stringList.add("ddd2");
        stringList.add("aaa2");
        stringList.add("bbb1");
        stringList.add("aaa1");
        stringList.add("bbb3");
        stringList.add("ccc");
        stringList.add("bbb2");
        stringList.add("ddd1");

        //stringList.stream().filter(s -> !s.equals("ccc")).forEach(System.out::println);

        //stringList.stream().map(String::toUpperCase).sorted((a,b)->b.compareTo(a)).forEach(System.out::println);


    }
}
