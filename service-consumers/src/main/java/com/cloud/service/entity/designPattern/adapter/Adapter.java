package com.cloud.service.entity.designPattern.adapter;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: Adapter
 * @description: 适配器模式
 * @date 2021/12/20 17:01
 */
public class Adapter implements AdapterChildren {
    public AdapterParent adapterParent;

    public Adapter(AdapterParent adapterParent) {
        this.adapterParent = adapterParent;
    }

    @Override
    public int get5V() {
        int sourceResult = adapterParent.get220V();
        return sourceResult / 44;
    }
}
