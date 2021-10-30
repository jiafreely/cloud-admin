package com.cloud.service.controller;


import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloud.service.config.HttpServletContextAware;
import com.cloud.service.entity.Teacher;
import com.cloud.service.result.R;
import com.cloud.service.service.TeacherService;
import com.cloud.service.util.RandomUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author xjh
 * @since 2021-08-12
 */

@Slf4j
@RestController
@Api(description = "教师")
@RequestMapping("/admin/produce/teacher")
public class TeacherController extends HttpServletContextAware {

    @Autowired
    private TeacherService teacherService;

    /**
     * @description: TODO
     * http请求获取
     * @return: com.cloud.service.result.R
     * @author: xjh
     * @date: 2021/8/18 11:00
     */
    @ApiOperation(value = "GetHttp服务提供者")
    @GetMapping("serviceProvider/{id}/ByHttpGet")
    public R serviceProviderByHttpGet(@PathVariable String id,String key1,String key2) {
        HttpServletRequest request = getRequest();
        log.info("Http服务调用:" + request.getHeader("JF_UR"));
        log.info("Http服务调用:" + id);
        log.info("Http服务调用:" + key1);
        log.info("Http服务调用:" + key2);
        log.info("Http服务调用:" + request.getHeader("X-Tenant-ID"));
        log.info("Http服务调用:" + request.getHeader("X-Request-ID"));
        return R.ok().message("success");
    }

    /**
     * @description: TODO
     * http请求获取
     * @return: com.cloud.service.result.R
     * @author: xjh
     * @date: 2021/8/18 11:00
     */
    @ApiOperation(value = "PostHttp服务提供者")
    @PostMapping("serviceProviderByHttpPost")
    public R serviceProviderByHttpPost(@RequestParam String key1, @RequestParam String key2) {
        HttpServletRequest request = getRequest();
        log.info("Http服务调用:" + request.getHeader("JF_UR"));
        log.info("Http服务调用:" + key1);
        log.info("Http服务调用:" + key2);
        log.info("Http服务调用:" + request.getHeader("X-Tenant-ID"));
        log.info("Http服务调用:" + request.getHeader("X-Request-ID"));
        return R.ok().message("success");
    }

    /**
     * @description: TODO
     * http请求获取
     * @return: com.cloud.service.result.R
     * @author: xjh
     * @date: 2021/8/18 11:00
     */
    @ApiOperation(value = "PutHttp服务提供者")
    @PutMapping("serviceProvider/{id}/ByHttpPut")
    public R serviceProviderByHttpPut(@PathVariable String id,String key1,String key2) {
        HttpServletRequest request = getRequest();
        log.info("Http服务调用:" + request.getHeader("JF_UR"));
        log.info("Http服务调用:" + id);
        log.info("Http服务调用:" + key1);
        log.info("Http服务调用:" + key2);
        log.info("Http服务调用:" + request.getHeader("X-Tenant-ID"));
        log.info("Http服务调用:" + request.getHeader("X-Request-ID"));
        return R.ok().message("success");
    }

    /**
     * @description: TODO
     * http请求获取
     * @return: com.cloud.service.result.R
     * @author: xjh
     * @date: 2021/8/18 11:00
     */
    @ApiOperation(value = "DeleteHttp服务提供者")
    @DeleteMapping("serviceProvider/{id}/ByHttpDelete")
    public R serviceProviderByHttpDelete(@PathVariable String id,String key1,String key2) {
        HttpServletRequest request = getRequest();
        log.info("Http服务调用:" + request.getHeader("JF_UR"));
        log.info("Http服务调用:" + id);
        log.info("Http服务调用:" + key1);
        log.info("Http服务调用:" + key2);
        log.info("Http服务调用:" + request.getHeader("X-Tenant-ID"));
        log.info("Http服务调用:" + request.getHeader("X-Request-ID"));
        return R.ok().message("success");
    }

    /**
     * @description: TODO
     * http请求获取
     * @return: com.cloud.service.result.R
     * @author: xjh
     * @date: 2021/8/18 11:00
     */
    @ApiOperation(value = "RestTemplate服务提供者")
    @GetMapping("serviceProviderByRestTemplate")
    public R serviceProviderByRestTemplate() throws Exception {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        HttpServletResponse response = requestAttributes.getResponse();
        String jf_sys = request.getHeader("SYS");
        String rid = request.getHeader("JF_RN");
        log.info("restTempalte服务调用:{}", jf_sys);
        String sid = UUID.fastUUID().toString().replaceAll("-", "");
        //response.addHeader("res", sid);
        System.out.println("res:" + sid);
        //teacherService.list()
        return R.ok().data("teacherList", "");
    }


    @ApiOperation(value = "查询全部教师")
    @GetMapping("queryAllList")
    public R queryAllList() {
        try {
            return R.ok().data("list", teacherService.list());
        } catch (Exception e) {
            log.info(e.getMessage());
            return R.error().message(e.getMessage());
        }
    }

    @ApiOperation(value = "根据id删除讲师")
    @DeleteMapping("deleteById/{id}")
    public R deleteById(@ApiParam(value = "讲师id", required = true) @PathVariable String id) {
        return R.ok().data("code", teacherService.removeById(id));
    }


    @ApiOperation(value = "查询教师:分页")
    @GetMapping("queryPageList")
    public R queryPageList() {
        Page<Teacher> page = new Page<>(1, 1);
        IPage<Teacher> teacherIPage = teacherService.page(page);
        return R.ok().data("list", teacherIPage.getRecords());
    }


    @ApiOperation(value = "根据id删除")
    @DeleteMapping("deleteTeacherById/{id}")
    public R deleteTeacherById(@ApiParam(value = "id", required = true) @PathVariable Long id) {
        return R.ok().data("code", teacherService.removeById(id));
    }


    @ApiOperation(value = "添加数据")
    @PostMapping("add")
    public R add() {
        log.info("添加请求进来了");
        Teacher teacher = new Teacher().setName(RandomUtils.getFourBitRandom()).setIntro(RandomUtils.getFourBitRandom()).setCareer(RandomUtils.getFourBitRandom()).setLevel(1);
        boolean save = teacherService.save(teacher);
        return R.ok().data("code", save);
    }


    @ApiOperation(value = "修改数据")
    @PostMapping("updateById")
    public R updateById(@ApiParam(value = "教师id", required = true) @RequestParam String id) {
        System.out.println("接收数据:{}" + id);
        Teacher teacher = teacherService.getById(id);
        teacher.setId(id);
        teacher.setName(RandomUtils.getSixBitRandom());
        boolean b = teacherService.updateById(teacher);
        return R.ok().data("code", b);
    }
}

