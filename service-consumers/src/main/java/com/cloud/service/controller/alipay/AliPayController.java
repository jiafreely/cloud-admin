package com.cloud.service.controller.alipay;

import cn.hutool.core.map.multi.ListValueMap;
import cn.hutool.http.server.HttpServerRequest;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.cloud.service.alipay.AlipayConfig;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: AliPayController
 * @description: TODO
 * @date 2021/10/22 15:40
 */

@Slf4j
@Controller
@Api(description = "支付宝支付", tags = "支付宝支付")
@RequestMapping("/admin/consumers/alipay")
public class AliPayController {

    /**
     * @description: 手机扫码付款
     * @param: httpRequest
     * @param: httpResponse
     * @return: void
     * @author: xjh
     * @date: 2021/10/22 16:25
     */
    @GetMapping("/transcation")
    public void transcation(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException, IOException {
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, AlipayConfig.format, AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);  //获得初始化的AlipayClient
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest(); //创建API对应的request​
        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = UUID.randomUUID().toString().substring(0, 13);
        //付款金额，必填
        String total_amount = new String("88.88");
        //订单名称，必填
        String subject = "冬天的第一杯奶茶";
        //商品描述，可空
        String body = new String("我的你的什么？你是我的优乐美");
        String bizContent = "{\"out_trade_no\":\"" + out_trade_no + "\","
                + "\"total_amount\":\"" + total_amount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"body\":\"" + body + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}";
        alipayRequest.setBizContent(bizContent);

//        alipayRequest.setBizContent(json);
        String form = "";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody();  //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        // 页面刷新会客户端
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setContentType("text/html;charset=UTF-8");
        httpResponse.getWriter().write(form); //直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
    }


    /**
     * @description: 手机扫码付款
     * @param: httpRequest
     * @param: httpResponse
     * @return: void
     * @author: xjh
     * @date: 2021/10/22 16:25
     */
    @GetMapping("/payPhone")
    public void payPhone(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception {
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, AlipayConfig.format, AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);  //获得初始化的AlipayClient
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest(); //创建API对应的request​

        alipayRequest.setNotifyUrl("http://10.111.26.200:8120/admin/consumers/alipay/error");
        alipayRequest.setReturnUrl("http://10.111.26.200:8120/admin/consumers/alipay/success");
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", UUID.randomUUID().toString());
        bizContent.put("total_amount", 0.01);
        bizContent.put("subject", "测试商品");
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");

        //// 商品明细信息，按需传入
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

    @GetMapping("/success")
    @ResponseBody
    public String success(HttpServerRequest request){
        ListValueMap<String, String> params = request.getParams();
        return "success";
    }
}
