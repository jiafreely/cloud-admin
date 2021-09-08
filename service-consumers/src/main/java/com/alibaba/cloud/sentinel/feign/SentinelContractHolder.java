package com.alibaba.cloud.sentinel.feign;

import feign.Contract;
import feign.MethodMetadata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: SentinelContractHolder
 * @description: 解决启动报错FactoryBean threw exception on object creation; nested exception is java.lang.AbstractMethodError: com.alibaba.cloud.sentinel.feign.SentinelContractHolder.parseAndValidatateMetadata(Ljava/lang/Class;)Ljava/util/List;
 * Sentinel框架SentinelContractHolder类中找不到parseAndValidatateMetadata这个方法，是因为这个方法拼写有错误，在Sentinel和OpenFeign新版本中已经修正为parseAndValidateMetadata
 * Sentinel（2.2.1.RELEASE）中的SentinelContractHolder类调用的是新版本的OpenFeign（2.2.2.RELEASE）方法
 * 而Spring Cloud Alibaba（2.2.1.RELEASE）引入的是旧版本的OpenFeign（2.2.1.RELEASE）方法
 *
 * 解决版本差异问题
 * 覆盖SentinelContractHolder类，使其继续调用旧版parseAndValidatateMetadata方法
 * 在项目模块中新建包：com.alibaba.cloud.sentinel.feign
 * @date 2021/8/27 17:24
 */
public class SentinelContractHolder implements Contract {
    private final Contract delegate;

    public static final Map<String, MethodMetadata> METADATA_MAP = new HashMap();

    public SentinelContractHolder(Contract delegate) {
        this.delegate = delegate;
    }
    @Override
    public List<MethodMetadata> parseAndValidatateMetadata(Class<?> targetType) {
        List<MethodMetadata> metadatas = this.delegate.parseAndValidatateMetadata(targetType);
        metadatas.forEach((metadata) ->
                METADATA_MAP.put(targetType.getName() + metadata.configKey(), metadata)
        );
        return metadatas;
    }
}
