package com.cloud.service.config.alipay;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: AlipayConfig
 * @description: TODO
 * https://blog.csdn.net/weixin_43218670/article/details/120875730?utm_medium=distribute.pc_feed_v2.none-task-blog-personrec_tag-17.pc_personrecdepth_1-utm_source=distribute.pc_feed_v2.none-task-blog-personrec_tag-17.pc_personrec
 * https://opendocs.alipay.com/open/204/105051 App 支付产品介绍
 * @date 2021/10/22 15:34
 */
@Data
@Configuration(proxyBeanMethods = false)
@ConfigurationProperties(prefix = "alipay") //自定义标识前缀
public class AlipayConfig {

    public String app_id;
    public String merchant_private_key;
    public String alipay_public_key;
    public String notify_url;
    public String return_url;
    public String sign_type;
    public String charset;
    public String gatewayUrl;
    public String format;

    // 支付宝网关
    public static String log_path;

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     *
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis() + ".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
