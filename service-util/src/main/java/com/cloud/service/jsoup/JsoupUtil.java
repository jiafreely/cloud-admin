package com.cloud.service.jsoup;

import com.cloud.service.DTO.JsoupKDTO;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: xiaoKJsoupUtil
 * @description: TODO
 * @date 2021/9/7 14:50
 */

@Slf4j
public class JsoupUtil {

    //小K
    private static final String XKURL = "https://www.kjsv.com/?jdfwkey=zhmb01";
    private static final String XKPreURL = "https://www.kjsv.com";

    //小刀
    private static final String XDURL = "https://www.xd0.com/";

    //流氓资源馆
    private static final String LMURL = "https://www.qqlmg.com/";

    public static List<JsoupKDTO> jsoupXk() {
        List<JsoupKDTO> jsoupKDTOList = new ArrayList<>();
        try {
            // 解析url地址
            Document document = Jsoup.parse(new URL(XKURL), 5000);
           /* // 获取title的内容
            Element title = document.getElementsByTag("title").first();
            //System.out.println(title.text());

            Element intt = document.getElementById("intt");
            //System.out.println(intt.text());*/

            //Selector选择器组合使用
            Elements elements = document.select("#intt li a");
            for (Element element : elements) {
                Attributes attributes = element.attributes();
                //地址
                String url = attributes.get("href");

                //标题
                Elements spanText = element.children().select("span");
                String titleName = spanText.text();

                //日期
                Elements spanI = element.children().select("i");
                String i = spanI.text();
                String substring = i.substring(i.indexOf("-") + 1, i.length());

                LocalDate jsouplocalDate = LocalDate.now().withDayOfMonth(Integer.valueOf(substring));
                LocalDate localDate = LocalDate.now();
                //爬取今天的内容信息
                if (localDate.toString().equals(jsouplocalDate.toString())) {
                    System.out.println(url + "\t" + titleName + "\t" + substring);
                    JsoupKDTO jsoupKDTO = new JsoupKDTO();
                    jsoupKDTO.setTitleName(titleName);
                    jsoupKDTO.setUrl(XKPreURL + url);
                    jsoupKDTO.setArticleTime(localDate);
                    jsoupKDTOList.add(jsoupKDTO);
                }
            }
        } catch (Exception e) {
            log.error("小k异常:{}", e.getMessage());
        }
        return jsoupKDTOList;
    }


    public static List<JsoupKDTO> jsoupXd() {
        List<JsoupKDTO> jsoupKDTOList = new ArrayList<>();
        try {
            // 解析url地址
            Document document = Jsoup.parse(new URL(XDURL), 5000);
            //Selector选择器组合使用
            Elements elements = document.select("[class=lbbt_c00]");
            for (Element element : elements) {
                //日期
                Elements span = element.children().select("span");
                String time = span.text();
                String substringTime = time.substring(time.indexOf("-") + 1, time.length());

                Elements a = element.children().select("a");
                //url
                String url = a.attr("href");
                //标题
                String titleName = a.text();

                LocalDate jsouplocalDate = LocalDate.now().withDayOfMonth(Integer.valueOf(substringTime));
                LocalDate localDate = LocalDate.now();
                //爬取今天的内容信息
                if (localDate.toString().equals(jsouplocalDate.toString())) {
                    System.out.println(url + "\t" + titleName + "\t" + substringTime);
                    JsoupKDTO jsoupKDTO = new JsoupKDTO();
                    jsoupKDTO.setTitleName(titleName);
                    jsoupKDTO.setUrl(XDURL + url);
                    jsoupKDTO.setArticleTime(localDate);
                    jsoupKDTOList.add(jsoupKDTO);
                }
            }
        } catch (Exception e) {
            log.error("小刀异常:{}", e.getMessage());
        }
        return jsoupKDTOList;
    }

    public static List<JsoupKDTO> jsoupLm() {
        List<JsoupKDTO> jsoupKDTOList = new ArrayList<>();
        try {
            // 解析url地址
            Document document = Jsoup.parse(new URL(LMURL), 5000);
            //Selector选择器组合使用
            Elements elements = document.select("[class=column half font]");
            for (Element element : elements) {
                //日期
                Elements span = element.children().select("span");
                String time = span.text();
                String substringTime = time.substring(time.indexOf("-") + 1, time.length());

                Elements a = element.children().select("a");
                //url
                String url = a.attr("href");
                //标题
                String titleName = a.text();

                LocalDate jsouplocalDate = LocalDate.now().withDayOfMonth(Integer.valueOf(substringTime));
                LocalDate localDate = LocalDate.now();
                //爬取今天的内容信息
                if (localDate.toString().equals(jsouplocalDate.toString())) {
                    System.out.println(url + "\t" + titleName + "\t" + substringTime);
                    JsoupKDTO jsoupKDTO = new JsoupKDTO();
                    jsoupKDTO.setTitleName(titleName);
                    jsoupKDTO.setUrl(LMURL + url);
                    jsoupKDTO.setArticleTime(localDate);
                    jsoupKDTOList.add(jsoupKDTO);
                }
            }
        } catch (Exception e) {
            log.error("流氓资源网:{}", e.getMessage());
        }
        return jsoupKDTOList;
    }
}
