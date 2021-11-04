package com.cloud.service.entity;

import java.io.Serializable;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: User
 * @description: TODO
 * @date 2021/11/4 9:48
 */
public class User implements Serializable {
    private String userName;
    public Integer age;

    public User() {
        System.out.println("无参构造函数-----------");
    }

    public User(String userName, Integer age) {
        System.out.println("有参构造函数-----------"+userName+age);
    }

    public void getUser(){
        System.out.println("公共无参方法");
    }
    public void getUser(String userName,Integer age){
        System.out.println("公共有参方法"+userName+age);
    }

    private void priUser(){
        System.out.println("私有无参方法");
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
