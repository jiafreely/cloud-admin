package com.cloud.service;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: UserTest
 * @description: TODO
 * @date 2021/11/4 9:51
 */
public class UserTest {
    public static void main(String[] args) throws Exception {
        //Class<User> userClass = User.class;
        //System.out.println("----------------------------获取公共属性");
        //Field[] publicfields = userClass.getFields();
        //Arrays.stream(publicfields).forEach(System.out::println);
        //Class<?> users = Class.forName("com.cloud.service.entity.User");
        //实例化
        //User u = (User)users.newInstance();
        //System.out.println("--------------------------获取构造函数");
        //Constructor<?> constructor1 = users.getConstructor(String.class, Integer.class);
        //User user1 = (User)constructor1.newInstance("张三", 1);
        //
        //Constructor<?> constructor2 = users.getConstructor();
        //User user2 = (User)constructor2.newInstance();

        //获取公共无参方法
        //Method getUser = users.getDeclaredMethod("getUser");
        //getUser.invoke(users.newInstance());

        //获取公共有参方法
        //通过获取的方法来调用(invoke),invoke方法有俩个参数
        //Method getUser = users.getDeclaredMethod("getUser",String.class,Integer.class);
        //getUser.invoke(u,"张三",3);

        //获取公共有参方法
        //Method getUser = users.getDeclaredMethod("priUser");
        //getUser.setAccessible(true);
        //getUser.invoke(u);
    }
}
