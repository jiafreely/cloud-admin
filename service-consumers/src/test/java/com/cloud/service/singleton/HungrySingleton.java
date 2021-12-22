package com.cloud.service.singleton;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: HungrySingleton
 * @description: 饿汉模式
 * @date 2021/12/21 11:43
 */
public class HungrySingleton {
    public static final HungrySingleton INSTANCE;

    private HungrySingleton() {

    }

    static {
        INSTANCE = new HungrySingleton();
    }
}
