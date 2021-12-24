package com.cloud.service.config.sentinel;

import com.alibaba.csp.sentinel.command.handler.ModifyParamFlowRulesCommandHandler;
import com.alibaba.csp.sentinel.datasource.*;
import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import com.alibaba.csp.sentinel.transport.util.WritableDataSourceRegistry;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: FileDataSourceInit
 * @description: 问题:每次重启微服务后Sentinel中的配置都会丢失，这是因为API将规则推送到了客户端的内存中，重启后就消失了
 * 在Sentinel Dashboard中设置规则之后，推送给客户端后不仅保存在内存中，还会保存到本地文件中。Pull模式需要通过代码实现
 * @date 2021/12/24 16:53
 */
@Slf4j
public class FileDataSourceInit implements InitFunc {
    @Override
    public void init() throws Exception {
        log.info("保存sentinel限流规则");
        //String ruleDir = System.getProperty("user.home") + "/sentinel/rules";
        String ruleDir = System.getProperty("user.dir") + "/sentinel/rules";
        log.info(ruleDir);
        //限流规则路径
        String flowRulePath = ruleDir + "/flow-rule.json";
        //降级规则路径
        String degradeRulePath = ruleDir + "/degrade-rule.json";
        //热点规则路径
        String paramFlowRulePath = ruleDir + "/param-flow-rule.json";
        //系统规则路径
        String systemRulePath = ruleDir + "/system-rule.json";
        //权限规则路径
        String authorityRulePath = ruleDir + "/authority-rule.json";

        this.mkdirIfNotExists(ruleDir);
        this.createFileIfNotExists(flowRulePath);
        this.createFileIfNotExists(flowRulePath);
        this.createFileIfNotExists(flowRulePath);
        this.createFileIfNotExists(flowRulePath);
        this.createFileIfNotExists(flowRulePath);

        // 流控规则，可读取数据
        ReadableDataSource<String, List<FlowRule>> flowRuleRDS = new FileRefreshableDataSource<>(
                flowRulePath,
                flowRuleListParser
        );
        //将可读数据源注入到FlowRuleManager,当文件发生变化时就会更新规则到缓存
        FlowRuleManager.register2Property(flowRuleRDS.getProperty());
        //流控规则：可写数据源
        WritableDataSource<List<FlowRule>> flowRuleWDS = new FileWritableDataSource<List<FlowRule>>(
                flowRulePath,
                this::encodeJson
        );
        WritableDataSourceRegistry.registerFlowDataSource(flowRuleWDS);

        //降级规则：可读数据源
        ReadableDataSource<String, List<DegradeRule>> degradeRuleRDS = new FileRefreshableDataSource<>(
                degradeRulePath,
                degradeRuleListParser
        );
        DegradeRuleManager.register2Property(degradeRuleRDS.getProperty());
        //降级规则：可写数据源
        WritableDataSource<List<DegradeRule>> degradeRuleWDS = new FileWritableDataSource<List<DegradeRule>>(
                flowRulePath,
                this::encodeJson
        );
        WritableDataSourceRegistry.registerDegradeDataSource(degradeRuleWDS);

        //热点参数：可读数据源
        ReadableDataSource<String, List<ParamFlowRule>> paramFlowRuleRDS = new FileRefreshableDataSource<>(
                paramFlowRulePath,
                paramFlowRuleListParser
        );
        ParamFlowRuleManager.register2Property(paramFlowRuleRDS.getProperty());
        //热点参数：可写数据源
        WritableDataSource<List<ParamFlowRule>> paramFlowRuleWDS = new FileWritableDataSource<List<ParamFlowRule>>(
                paramFlowRulePath,
                this::encodeJson
        );
        ModifyParamFlowRulesCommandHandler.setWritableDataSource(paramFlowRuleWDS);

        //系统规则：可读数据源
        ReadableDataSource<String, List<SystemRule>> systemRuleRDS = new FileRefreshableDataSource<>(
                systemRulePath,
                systemRuleListParser
        );
        SystemRuleManager.register2Property(systemRuleRDS.getProperty());
        //系统规则：可写数据源
        WritableDataSource<List<SystemRule>> systemRuleWDS = new FileWritableDataSource<List<SystemRule>>(
                systemRulePath,
                this::encodeJson
        );
        WritableDataSourceRegistry.registerSystemDataSource(systemRuleWDS);

        //授权规则：可读数据源
        ReadableDataSource<String, List<AuthorityRule>> authorityRuleRDS = new FileRefreshableDataSource<>(
                authorityRulePath,
                authorityRuleListParser
        );
        AuthorityRuleManager.register2Property(authorityRuleRDS.getProperty());
        WritableDataSource<List<AuthorityRule>> authorityRuleWDS = new FileWritableDataSource<List<AuthorityRule>>(
                authorityRulePath,
                this::encodeJson
        );
        WritableDataSourceRegistry.registerAuthorityDataSource(authorityRuleWDS);
    }

    private Converter<String, List<FlowRule>> flowRuleListParser = source -> JSON.parseObject(
            source,
            new TypeReference<List<FlowRule>>() {
            }
    );
    private Converter<String, List<DegradeRule>> degradeRuleListParser = source -> JSON.parseObject(
            source,
            new TypeReference<List<DegradeRule>>() {
            }
    );
    private Converter<String, List<SystemRule>> systemRuleListParser = source -> JSON.parseObject(
            source,
            new TypeReference<List<SystemRule>>() {
            }
    );
    private Converter<String, List<AuthorityRule>> authorityRuleListParser = source -> JSON.parseObject(
            source,
            new TypeReference<List<AuthorityRule>>() {
            }
    );
    private Converter<String, List<ParamFlowRule>> paramFlowRuleListParser = source -> JSON.parseObject(
            source,
            new TypeReference<List<ParamFlowRule>>() {
            }
    );


    private void mkdirIfNotExists(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    private void createFileIfNotExists(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            file.createNewFile();
        }
    }

    private <T> String encodeJson(T t) {
        return JSON.toJSONString(t);
    }

}
