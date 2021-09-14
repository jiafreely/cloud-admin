package com.cloud.service.controller;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
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
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

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
    private static final String XIAOK="小k娱乐网";
    private static final String XIAOKSearch="https://www.kjsv.com/search/comprehensive.do";


    private static final String XIAOD="小刀娱乐网";

    @Autowired
    private XiaoKService xiaoKService;

    @ApiOperation(value = "聚合爬取当天数据")
    @PostMapping("addJsoup")
    public R addJsoup() {
        log.info("---------------------开始爬取小k娱乐网的内容");
        List<JsoupKDTO> jsoupKDTOList = JsoupUtil.jsoupXk();
        if (jsoupKDTOList != null && jsoupKDTOList.size() > 0) {
            jsoupKDTOList.forEach(jsoupKDTO -> {
                JsoupInfo jsoupInfo = new JsoupInfo().setTitleInfo(XIAOK).setTitleName(jsoupKDTO.getTitleName()).setUrl(jsoupKDTO.getUrl()).setArticleTime(jsoupKDTO.getArticleTime());
                xiaoKService.save(jsoupInfo);
            });
        }
        log.info("---------------------爬取小k娱乐网的内容爬取完毕,共爬取{}条内容", jsoupKDTOList.size());

        log.info("---------------------开始爬取小刀娱乐网的内容");
        List<JsoupKDTO> jsoupDDTOList = JsoupUtil.jsoupXd();
        if (jsoupDDTOList != null && jsoupDDTOList.size() > 0) {
            jsoupDDTOList.forEach(jsoupKDTO -> {
                JsoupInfo jsoupInfo = new JsoupInfo().setTitleInfo(XIAOD).setTitleName(jsoupKDTO.getTitleName()).setUrl(jsoupKDTO.getUrl()).setArticleTime(jsoupKDTO.getArticleTime());
                xiaoKService.save(jsoupInfo);
            });
        }
        log.info("---------------------爬取小刀娱乐网的内容爬取完毕,共爬取{}条内容", jsoupDDTOList.size());

        return R.ok().message("爬取小k娱乐网的内容爬取完毕,共爬取"+jsoupKDTOList.size()+"条内容,爬取小刀娱乐网的内容爬取完毕,共爬取"+jsoupDDTOList.size()+"条内容");
    }

    @ApiOperation(value = "爬取查询内容")
    @GetMapping("jsoupQueryList")
    public JSONObject jsoupQueryList(@ApiParam(value = "内容",required = true) @RequestParam(value = "keyWord") String keyWord){
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
    public R showAllList(){
        List<JsoupInfo> list = xiaoKService.list();
        return R.ok().data("success",list);
    }

    @ApiOperation(value = "分页查询数据")
    @GetMapping("showPageList")
    public R showPageList(@ApiParam(value = "页码",required = true,defaultValue = "1") @RequestParam(value = "pageNo") long pageNo,
                          @ApiParam(value = "条数",required = true,defaultValue = "5") @RequestParam(value = "pageSize") long pageSize){
        Page<JsoupInfo> page = new Page<>(pageNo, pageSize);
        IPage<JsoupInfo> teacherIPage =xiaoKService.page(page);
        return R.ok().data("list",teacherIPage.getRecords());
    }
}
