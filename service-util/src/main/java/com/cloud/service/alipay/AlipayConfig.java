package com.cloud.service.alipay;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: AlipayConfig
 * @description: TODO
 * https://blog.csdn.net/weixin_43218670/article/details/120875730?utm_medium=distribute.pc_feed_v2.none-task-blog-personrec_tag-17.pc_personrecdepth_1-utm_source=distribute.pc_feed_v2.none-task-blog-personrec_tag-17.pc_personrec
 * @date 2021/10/22 15:34
 */
public class AlipayConfig {
    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2016101000656728";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCRIEEA+m+i+DL9vQE619mx62JOWMOGqEA4aW4ERNuOdLC28qf4vlEFfAdQcgpcExnorTVTMd5hqVF32fAtRaBYC+gKFMAecZo92sRBROyrCmwUO6dRbeeOVa/FM19OPsornBC5b7GvG5leFsj5s2baII9x2e5ZJQH2H1RTUjyD7o9cCLmS7LBPxijN/CNBkgVA34wl+fbsXjvqzkqrw+E3oJHIJHtWhbit1vl1AgXoiXOwC0NWjSc6DcyWRDSR8MyikoOBzTrZWXOFiSjqDXacXlj8m4MYMiFl2htVkQcYcL15rnj30taRxU/8Ut7BmrJn4H8VDFZxOV9hbf5S9IM7AgMBAAECggEAcEVPhH45lw4EBKAPL4KE+0tpxcqNmMLYy8tkPRE3+UD05NNcduZkr6V/0xAmS4zLYmF8+1t8+niOefW0BJH0taigXgwnhGXGOUvgv82eecOaURzFGA26MTbAwAI6HMIejRYzKgHqp9jmEQ/3cpUDYIQH9L/v0C79EXazObJI9paQZFT5UhnDPP4E5z56U0oVLYI5fiZmCW4GTF/3d7u2f2Qrpprwo9zY6OV3pGUl5cDh+ojazc0egM13aS1/GaEYKpXh9/jGzni9PE7boUCMh80bJdS9Hsw6qSynYPx9SWtechijE0kFCXuh4XVhd9k4ko3ngER4PQhHeP38lbDBgQKBgQDP4pWvlUu/14vvRbOuv0hp1PayXyTSNxjYXQivJ2uv6gzk+ep7PfcGMOfxIFLDegRUSHq8rqPPA6fFETvKrKApuhKxLmtVMqN08mlfvZOzyfxykzT4Sqid6tF78OzZhEEM3bnDthHGBrpvNGf2yFFGwUAvMm7Qv/FZ1OPqvJta0QKBgQCytx/1ncwibHdfVdAxXUi8fIjwZUz5AIu8u5Fbp6IabJDu21JNLLTtO6TtGHPT4Kkn6RhoANcgCMXsvrYrSeqcbS9QBiJiB5REOs0ZQVamM1dSbpJVyZeF6tlbuV6RHxRSw/N4V6XBnrDnDofnmqEDiQZWjz3Duxg2Vs6ttsxoSwKBgQDBjcsmpw/XN75/cltg+ZJXj0R+QV3MKCaG1OBQDJkUOgv1acdrJf+IkSJzfE/y1vmO68jzQnscNOAQ+QG9q8qX7zqWI40lzzcnnMiFrsIF5Kualfr8rq6eEdMn3eL4h031x7+9PBkCUqNlBNtDh7E8+pNjHkbVL9FsfW4T0bWn8QKBgGWcdRFjjMMtxJlx4xOOJGL2s61rC16wDsNaKgVgxGwdujH0hVDX0G7p8JXVx1V6cPL+NyA7ChVup4o5zL3EIx6ZfcMUGcx+3rAEdsSbP7bzjfySXaVlHz/HDZp3ROtiLl34h5+uxdHWdmKJAoCOjGnEKNRiUQ3OfK+4n35pZsMNAoGADFZ+S0xfeM3ppaZ7yRLvIbfbsi2w9qyBoZIfWEEJ1IJPGLJKTyw/aCRLd5KRcrii/A7uVE5+hEP2AnwXFcjM5fbsKV99uqdIQBAKJ7X23tvSFlC75JWL8e8OzTaIRH3UgxCjxhYkz9uDHXF+z8DK6RQYAAjbHUWPfZMRfk5xl58=";
    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAn3EQ53xxawpQNJ3nUVoQR2pcOQ8SkmFPXUIz1ZkVemraOlq3BOQ8qy5DHUbYBriHW/69A5ddTZ0XNiI3rRuJ9gU4H7guRakuRIHcAWREI5sK+qF0AWAcW0WkOXc1zLv9zvR9nBYmtGzbzF+ayU8/z5/2HyG8Xx/rqgWuRbHJPie98NdhhkRvaKZZHJMweE8FdZ8yrfUsfh+VQny66ptNEXEJbjexfARePcWeiz/4TVtmue8kQ5FvrOiOgVO8EegW7XJrioPzUl1YRlhyvYgx4jM6sJo1A39torPYHFX5Z97jtim+CavuyZVlfxKuEtx1jDRTxAG3ZkM4m/t+yXJqRwIDAQAB";
    // 下面是两个回调地址，指支付成功后用户会跳转到哪些页面，不填也可以
    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://工程公网访问地址/alipay.trade.page.pay-JAVA-UTF-8/notify_url.jsp";
    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://工程公网访问地址/alipay.trade.page.pay-JAVA-UTF-8/return_url.jsp";
    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "UTF-8";

    // 沙箱支付宝网关 正式支付网关是 https://openapi.alipay.com/gateway.do 千万不要混淆了
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
    public static String log_path = "D:\\";

    // json格式
    public static String format = "json";

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
