package com.cloud.service.designPattern;

import com.cloud.service.entity.designPattern.adapter.Adapter;
import com.cloud.service.entity.designPattern.adapter.AdapterChildren;
import com.cloud.service.entity.designPattern.adapter.AdapterParent;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: AdapterTest
 * @description: 适配器模式
 * @date 2021/12/20 17:02
 */
public class AdapterTest {
    public static void main(String[] args) {
        AdapterChildren adapterChildren = new Adapter(new AdapterParent());
        System.out.println(adapterChildren.get5V());
    }
}
