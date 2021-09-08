package com.cloud.service;

import cn.hutool.http.HttpUtil;
import org.joda.time.LocalDate;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;
import java.util.HashMap;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: JsoupTest
 * @description: 爬虫测试类
 * https://blog.csdn.net/qq_44797965/article/details/108251164
 * @date 2021/9/7 9:46
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceProduceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JsoupTest {
    @Test
    public void testJsoupUrl() throws Exception {
        // 解析url地址
        Document document = Jsoup.parse(new URL("https://www.kjsv.com/?jdfwkey=zhmb01"), 1000);
        // 获取title的内容
        Element title = document.getElementsByTag("title").first();
        //System.out.println(title.text());

        Element intt = document.getElementById("intt");
        //System.out.println(intt.text());

        //Selector选择器组合使用
        Elements elements = document.select("#intt li a");
        for (Element element : elements) {
            Attributes attributes = element.attributes();
            String href = attributes.get("href");
            Elements spanText = element.children().select("span");
            String span = spanText.text();
            Elements spanI = element.children().select("i");
            String i = spanI.text();
            String substring = i.substring(i.indexOf("-") + 1, i.length());
            LocalDate jsouplocalDate = LocalDate.now().withDayOfMonth(Integer.valueOf(substring));
            LocalDate localDate = LocalDate.now();
            //爬取今天的内容信息
            if(localDate.toString().equals(jsouplocalDate.toString())){
                System.out.println(href + "\t" + span + "\t" + substring);
            }


        }

    }

    @Test
    public void testXkHttpUrl() throws Exception {
        //HttpRequest request = HttpRequest.get("https://www.kjsv.com/search/all?keywords=1");
        //HttpResponse response = request.execute();
        //String s = response.body();


        //POST请求
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("keywords", "1");
        paramMap.put("pageNo", "1");
        paramMap.put("pageSize", "5");
        String post = HttpUtil.post("https://www.kjsv.com/search/comprehensive.do", paramMap);
        System.out.println(post);
    }

    @Test
    public void testXd0Url() throws Exception {
        // 解析url地址
        Document document = Jsoup.parse(new URL("https://www.xd0.com/"), 5000);
        //Selector选择器组合使用
        Elements elements = document.select("[class=lbbt_c00]");
        for (Element element : elements) {
            Elements span = element.children().select("span");
            String time = span.text();
            Elements a = element.children().select("a");
            String url = a.attr("href");
            String title=a.text();

            System.out.println(time+title+url);
        }
    }

    //public static void main(String[] args) throws IOException {
    //    Document document = Jsoup.parse(new URL("http://www.z-sms.com/"), 5000);
    //
    //    //Selector选择器组合使用
    //    Elements elements = document.select("[class=btnCopy]");
    //    for (Element element : elements) {
    //        String elphone = element.text();
    //        //19815151534
    //        String phone = elphone.substring(0, elphone.length() - 2);
    //        System.out.println(phone);
    //    }
    //}
}
