package com.cloud.service.singleton;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: LazySingleton
 * @description: 懒汉模式
 * @date 2021/12/21 14:18
 */
public class LazySingleton {
    private LazySingleton() {
    }

    private static volatile LazySingleton INSTANCE;

    public static LazySingleton getInstance() {
        if (INSTANCE == null) {
            synchronized (LazySingleton.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LazySingleton();
                }
            }
        }
        return INSTANCE;
    }
}
