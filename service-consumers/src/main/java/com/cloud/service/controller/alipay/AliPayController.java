package com.cloud.service.controller.alipay;

import cn.hutool.http.server.HttpServerRequest;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.internal.util.StringUtils;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.cloud.service.config.alipay.AlipayConfig;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: AliPayController
 * @description: TODO
 * https://blog.csdn.net/weixin_43218670/article/details/120875730?utm_medium=distribute.pc_feed_v2.none-task-blog-personrec_tag-17.pc_personrecdepth_1-utm_source=distribute.pc_feed_v2.none-task-blog-personrec_tag-17.pc_personrec
 * @date 2021/10/22 15:40
 */

@Slf4j
@Controller
@Api(description = "支付宝支付", tags = "支付宝支付")
@RequestMapping("/admin/consumers/alipay")
public class AliPayController {

    @Autowired
    private AlipayConfig alipayConfig;

    /**
     * @description: 手机扫码付款
     * @param: httpRequest
     * @param: httpResponse
     * @return: void
     * @author: xjh
     * https://blog.csdn.net/weixin_46945684/article/details/108394747?ops_request_misc=&request_id=&biz_id=102&utm_term=java%E6%94%AF%E4%BB%98%E5%AE%9D%E6%89%AB%E7%A0%81%E6%94%AF%E4%BB%98&utm_medium=distribute.pc_search_result.none-task-blog-2~all~sobaiduweb~default-1-108394747.nonecase&spm=1018.2226.3001.4187
     * @date: 2021/10/22 16:25
     */
    @GetMapping("/transcation")
    public String transcation(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException, IOException {
        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig.getGatewayUrl(), alipayConfig.getApp_id(), alipayConfig.getMerchant_private_key(), alipayConfig.getFormat(), alipayConfig.getCharset(), alipayConfig.getAlipay_public_key(), alipayConfig.getSign_type());  //获得初始化的AlipayClient
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest alipayRequest = new AlipayTradeAppPayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        //商品描述，可空
        model.setBody("我是你的什么？你是我的优乐美");
        //订单名称，必填
        model.setSubject("冬天的第一杯奶茶");
        //商户订单号，商户网站订单系统中唯一订单号，必填
        model.setOutTradeNo(UUID.randomUUID().toString().substring(0, 13));
        model.setTimeoutExpress("30m");
        //付款金额，必填
        model.setTotalAmount("6.66");
        model.setProductCode("QUICK_MSECURITY_PAY");
        //将自己想要传递到异步接口的数据，set进去 pass_back_params
        model.setPassbackParams(UUID.randomUUID().toString().substring(0, 13));
        alipayRequest.setBizModel(model);
        alipayRequest.setNotifyUrl(alipayConfig.getNotify_url());
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(alipayRequest);  //调用SDK生成表单
            String body = response.getBody();
            //todo 将body返回给前端生成二维码
            return body;
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * @description: 手机(网页)扫码付款--会生成页面
     * @param: httpRequest
     * @param: httpResponse
     * @return: void
     * @author: xjh
     * @date: 2021/10/22 16:25
     */
    @GetMapping("/payPhone")
    public void payPhone(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig.getGatewayUrl(), alipayConfig.getApp_id(), alipayConfig.getMerchant_private_key(), alipayConfig.getFormat(), alipayConfig.getCharset(), alipayConfig.getAlipay_public_key(), alipayConfig.getSign_type());  //获得初始化的AlipayClient
        //创建API对应的request​
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();

        //服务器异步通知页面路径
        alipayRequest.setNotifyUrl(alipayConfig.getNotify_url());
        //页面跳转同步通知页面路径
        alipayRequest.setReturnUrl(alipayConfig.getReturn_url());
        JSONObject bizContent = new JSONObject();
        //支付单号，用于辨别是否重复确认，支付宝那边会有一套机制防止用户重复支付一个单号的订单
        bizContent.put("out_trade_no", UUID.randomUUID().toString());
        //支付金额，单位为元，可达到小数点后两位，如88.88表示88元8角8分
        bizContent.put("total_amount", 0.01);
        //支付时显示订单标题
        bizContent.put("subject", "测试商品");
        //产品码 https://opendocs.alipay.com/apis/api_1/alipay.trade.pay?scene=common
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");

        //// 商品明细信息，按需传入 订单包含的商品列表信息，json格式
        //JSONArray goodsDetail = new JSONArray();
        //JSONObject goods1 = new JSONObject();
        //goods1.put("goods_id", "goodsNo1");
        //goods1.put("goods_name", "子商品1");
        //goods1.put("quantity", 1);
        //goods1.put("price", 0.01);
        //goodsDetail.add(goods1);
        //bizContent.put("goods_detail", goodsDetail);

        //// 扩展信息，按需传入
        //JSONObject extendParams = new JSONObject();
        //extendParams.put("sys_service_provider_id", "2088511833207846");
        //bizContent.put("extend_params", extendParams);

        alipayRequest.setBizContent(bizContent.toString());

        AlipayTradePagePayResponse response = alipayClient.pageExecute(alipayRequest);

        if (response.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }

        // 页面刷新会客户端
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setContentType("text/html;charset=UTF-8");
        httpResponse.getWriter().write(response.getBody()); //直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
    }

    @GetMapping("/returnUrl")
    @ResponseBody
    public String returnUrl(HttpServletRequest request) {
        //获取支付宝POST过来反馈信息
        // https://docs.open.alipay.com/54/106370
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        try {
            //切记alipaypublickey是支付宝的公钥
            boolean flag = AlipaySignature.rsaCheckV1(params, alipayConfig.getAlipay_public_key(), alipayConfig.getCharset(), alipayConfig.getSign_type());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //todo 执行业务逻辑
        return "success";
    }

    @PostMapping("/notifyUrl")
    @ResponseBody
    public String notifyUrl(HttpServerRequest request) {
        System.err.println("success");
        System.err.println("notifyUrl");
        return "success";
    }


    /**
     * @description: 根据订单id获取订单信息
     * @param:tradeNo
     * @return:String
     * https://opendocs.alipay.com/open/02e7gm
     * @author: xjh
     * @date: 2021/10/26 15:19
     */
    @GetMapping("/tradeQuery/{tradeNo}")
    @ResponseBody
    public String tradeQuery(@PathVariable String tradeNo) throws Exception {
        if (StringUtils.isEmpty(tradeNo)) {
            return "订单号不能为空";
        }
        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig.getGatewayUrl(), alipayConfig.getApp_id(), alipayConfig.getMerchant_private_key(), alipayConfig.getFormat(), alipayConfig.getCharset(), alipayConfig.getAlipay_public_key(), alipayConfig.getSign_type());
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", tradeNo);
        //trade_no or out_trade_no 两个不允许同时为空
        //bizContent.put("trade_no", "2014112611001004680073956707");
        request.setBizContent(bizContent.toString());
        AlipayTradeQueryResponse response = alipayClient.execute(request);
        if (response.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
        return response.getBody();
    }
}
