package com.cloud.service.util;

import java.util.Arrays;
import java.util.BitSet;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: BloomFilter
 * @description: 布隆过滤器
 * 用来过滤非法key，避免缓存穿透（请求直接打到数据库），布隆过滤器底层用的是位数组，不仅节省空间，性能也猛，而且占用内存不会随着使用变大
 * @date 2021/12/29 16:08
 */
public class BloomFilter {
    //后面hash函数会用到，用来生成不同的hash值，可以随便给，但别给奇数
    private final int[] ints = {6, 8, 16, 38, 58, 68};
    private Integer currentBeanCount = 0;
    //你的布隆过滤器容量
    private int DEFAULT_SIZE = Integer.MAX_VALUE;
    //bit数组，用来存放结果
    private final BitSet bitSet = new BitSet(DEFAULT_SIZE);

    public BloomFilter() {
    }

    public BloomFilter(int size) {
        if (size > Integer.MAX_VALUE) {
            throw new RuntimeException("size is too large");
        }
        if (size <= (2 << 8)) {
            throw new RuntimeException("size is too small");
        }
        DEFAULT_SIZE = size;
    }

    //获取当前过滤器的对象数量
    public Integer getCurrentBeanCount() {
        return currentBeanCount;
    }

    //计算出key的hash值，并将对应下标置为true
    public void push(Object key) {
        Arrays.stream(ints).forEach(i -> bitSet.set(hash(key, i)));
        currentBeanCount++;
    }

    //判断key是否存在，true不一定说明key存在，但是false一定说明不存在
    public boolean contain(Object key) {
        boolean result = true;
        for (int i : ints) {
            result = result && bitSet.get(hash(key, i));
        }
        return result;
    }

    //hash算法，借鉴了hashmap的算法
    private int hash(Object key, int i) {
        int h;
        int index = key == null ? 0 : (DEFAULT_SIZE - 1 - i) & ((h = key.hashCode()) ^ (h >>> 16));
        return index > 0 ? index : -index;
    }

    public static void main(String[] args) {
        BloomFilter myNewBloomFilter = new BloomFilter();
        myNewBloomFilter.push("张学友");
        myNewBloomFilter.push("郭德纲");
        myNewBloomFilter.push("蔡徐鸡");
        myNewBloomFilter.push(666);
        System.out.println(myNewBloomFilter.contain("张学友"));//true
        System.out.println(myNewBloomFilter.contain("张学友 "));//false
        System.out.println(myNewBloomFilter.contain("张学友1"));//false
        System.out.println(myNewBloomFilter.contain("郭德纲"));//true
        System.out.println(myNewBloomFilter.contain("蔡徐老母鸡"));//false
        System.out.println(myNewBloomFilter.contain(666));//true
        System.out.println(myNewBloomFilter.contain(888));//false

    }
}
