package com.cloud.service.controller;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloud.service.DTO.JsoupKDTO;
import com.cloud.service.entity.JsoupInfo;
import com.cloud.service.jsoup.JsoupUtil;
import com.cloud.service.result.R;
import com.cloud.service.service.XiaoKService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: XiaoKController
 * @description: TODO
 * @date 2021/9/7 11:41
 */

@Slf4j
@RestController
@Api(description = "聚合爬取")
@RequestMapping("/admin/xiaok")
public class JsoupController {
    private static final String XIAOK = "小k娱乐网";
    private static final String XIAOKSearch = "https://www.kjsv.com/search/comprehensive.do";


    private static final String XIAOD = "小刀娱乐网";

    private static final String LIUM = "流氓资源馆";
    @Autowired
    private ThreadPoolTaskExecutor poolTaskExecutor;
    @Autowired
    private XiaoKService xiaoKService;
    /**
     * countDownLatch是一个计数器，线程完成一个记录一个，计数器递减，只能只用一次
     */
    CountDownLatch countDownLatch = new CountDownLatch(3);

    @ApiOperation(value = "聚合爬取当天数据")
    @PostMapping("addJsoup")
    public R addJsoup() throws Exception {
        Future<Integer> submitXk = poolTaskExecutor.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log.info("---------------------开始爬取小k娱乐网的内容");
                List<JsoupKDTO> jsoupKDTOList = JsoupUtil.jsoupXk();
                if (jsoupKDTOList != null && jsoupKDTOList.size() > 0) {
                    jsoupKDTOList.forEach(jsoupKDTO -> {
                        JsoupInfo jsoupInfo = new JsoupInfo().setTitleInfo(XIAOK).setTitleName(jsoupKDTO.getTitleName()).setUrl(jsoupKDTO.getUrl()).setArticleTime(jsoupKDTO.getArticleTime());
                        xiaoKService.save(jsoupInfo);
                        countDownLatch.countDown();
                    });
                }
                log.info("---------------------爬取小k娱乐网的内容爬取完毕,共爬取{}条内容", jsoupKDTOList.size());
                return jsoupKDTOList.size();
            }
        });

        Future<Integer> submitXd = poolTaskExecutor.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log.info("---------------------开始爬取小刀娱乐网的内容");
                List<JsoupKDTO> jsoupDDTOList = JsoupUtil.jsoupXd();
                if (jsoupDDTOList != null && jsoupDDTOList.size() > 0) {
                    jsoupDDTOList.forEach(jsoupKDTO -> {
                        JsoupInfo jsoupInfo = new JsoupInfo().setTitleInfo(XIAOD).setTitleName(jsoupKDTO.getTitleName()).setUrl(jsoupKDTO.getUrl()).setArticleTime(jsoupKDTO.getArticleTime());
                        xiaoKService.save(jsoupInfo);
                        countDownLatch.countDown();
                    });
                }
                log.info("---------------------爬取小刀娱乐网的内容爬取完毕,共爬取{}条内容", jsoupDDTOList.size());
                return jsoupDDTOList.size();
            }
        });


        Future<Integer> submitLm = poolTaskExecutor.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log.info("---------------------开始爬取流氓资源网的内容");
                List<JsoupKDTO> jsoupMDTOList = JsoupUtil.jsoupLm();
                if (jsoupMDTOList != null && jsoupMDTOList.size() > 0) {
                    jsoupMDTOList.forEach(jsoupMDTO -> {
                        JsoupInfo jsoupInfo = new JsoupInfo().setTitleInfo(LIUM).setTitleName(jsoupMDTO.getTitleName()).setUrl(jsoupMDTO.getUrl()).setArticleTime(jsoupMDTO.getArticleTime());
                        xiaoKService.save(jsoupInfo);
                    });
                }
                log.info("---------------------爬取流氓资源网的内容爬取完毕,共爬取{}条内容", jsoupMDTOList.size());
                return jsoupMDTOList.size();
            }
        });

        return R.ok().message("爬取小k娱乐网的内容爬取完毕,共爬取" + submitXk.get() + "条内容,爬取小刀娱乐网的内容爬取完毕,共爬取" + submitXd.get()+ "条内容,爬取流氓资源网的内容爬取完毕,共爬取" + submitLm.get() + "条内容");
    }

    @ApiOperation(value = "爬取查询内容")
    @GetMapping("jsoupQueryList")
    public JSONObject jsoupQueryList(@ApiParam(value = "内容", required = true) @RequestParam(value = "keyWord") String keyWord) {
        //POST请求
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("keywords", keyWord);
        paramMap.put("pageNo", "1");
        paramMap.put("pageSize", "5");
        String post = HttpUtil.post(XIAOKSearch, paramMap);
        JSONObject jsonObject = JSONUtil.parseObj(post);
        return jsonObject;
    }


    @ApiOperation(value = "查询全部数据")
    @GetMapping("showAllList")
    public R showAllList() {
        List<JsoupInfo> list = xiaoKService.list();
        return R.ok().data("success", list);
    }

    @ApiOperation(value = "分页查询数据")
    @GetMapping("showPageList")
    public R showPageList(@ApiParam(value = "页码", required = true, defaultValue = "1") @RequestParam(value = "pageNo") long pageNo,
                          @ApiParam(value = "条数", required = true, defaultValue = "5") @RequestParam(value = "pageSize") long pageSize) {
        Page<JsoupInfo> page = new Page<>(pageNo, pageSize);
        IPage<JsoupInfo> teacherIPage = xiaoKService.page(page);
        return R.ok().data("list", teacherIPage.getRecords());
    }

    @ApiOperation(value = "查询今天的数据")
    @GetMapping("showToDayList")
    public R showToDayList() {
        LambdaQueryWrapper<JsoupInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(JsoupInfo::getArticleTime, LocalDate.now());
        List<JsoupInfo> Datelist = xiaoKService.list(lambdaQueryWrapper);
        return R.ok().data("list", Datelist);
    }

    @ApiOperation(value = "EasyExcel读取今日的数据")
    @PostMapping("simpleWrite")
    public R simpleWrite() {
        LambdaQueryWrapper<JsoupInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(JsoupInfo::getArticleTime, LocalDate.now());
        List<JsoupInfo> Datelist = xiaoKService.list(lambdaQueryWrapper);
        EasyExcel.write("爬虫信息表" + System.currentTimeMillis() + ".xlsx", JsoupInfo.class).sheet().doWrite(Datelist);
        return R.ok().message("读取成功");
    }
}
