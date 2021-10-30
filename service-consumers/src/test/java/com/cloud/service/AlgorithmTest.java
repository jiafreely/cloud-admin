package com.cloud.service;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: AlgorithmTest
 * @description: 快速排序
 * @date 2021/10/26 16:01
 */
public class AlgorithmTest {
    /**
     * @description: 快速排序
     * @param:
     * @return: void
     * https://www.sohu.com/a/246785807_684445/
     * @author: xjh
     * @date: 2021/10/26 16:05
     */
    @Test
    public void quickSort() {
        int[] arr = new int[]{4, 7, 6, 5, 3, 2, 8, 1};
        System.out.println(arr.length);
    }

    /**
     * @description: 冒泡排序
     * @param:
     * @return: void
     * @author: xjh
     * @date: 2021/10/26 16:19
     */
    @Test
    public void bubbleSorting() {
        int[] arr = new int[]{4, 7, 6, 5, 3, 2, 8, 1};
    }

    /**
     * @description: 二分法排序
     * @param:
     * @return: void
     * @author: xjh
     * @date: 2021/10/26 16:17
     */
    @Test
    public void dichotomy() {
        int[] arr = new int[]{4, 7, 6, 5, 3, 2, 8, 1};
        int searchWord = 9;
        //二分法查找前对元素进行排序
        Arrays.sort(arr);

        // 最小索引
        int min = 0;
        // 最大索引
        int max = arr.length - 1;
        // 中间索引
        int center = (max + min) / 2;
        while (min <= max) {
            if (arr[center] == searchWord) {
                System.out.println("索引为:" + center);
                break;
            } else if (arr[center] > searchWord) {
                max = center - 1;
            } else if (arr[center] < searchWord) {
                min = center + 1;
            }
            center = (max + min) / 2;
            if (max < 0 || min > arr.length - 1) {
                System.out.println("没有找到");
            }
        }
    }
}
