package com.cloud.service;

import com.cloud.service.entity.User;
import com.cloud.service.util.RandomUtils;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: TestLambda
 * @description: TODO
 * @date 2021/11/4 16:57
 */
public class TestLambda {
    /*
     * @description: TODO
     * @param: null
     * @return:
     * @author: xjh
     * @date: 2021/11/5 21:52
     */


    public static void main(String[] args) {
        List<User> userList = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            User user = new User();
            user.setAge(i);
            user.setUserName(RandomUtils.getFourBitRandom());
            userList.add(user);
        }
        //按每5000个一组分割
        List<List<User>> parts = Lists.partition(userList, 20);
        parts.stream().forEach(System.out::println);
    }
}
