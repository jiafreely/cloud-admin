package com.cloud.service.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @ApiOperation(value = "查询全部教师")
    @GetMapping("queryAllList")
    public R queryAllList() {
        return R.ok().data("list", teacherService.list());
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
    public R deleteTeacherById(@ApiParam(value = "id",required = true) @PathVariable Long id) {
        return R.ok().data("code", teacherService.removeById(id));
    }

    @ApiOperation(value = "添加数据")
    @PostMapping("add")
    public R add() {
        Teacher teacher = new Teacher().setName(RandomUtils.getFourBitRandom()).setIntro(RandomUtils.getFourBitRandom()).setCareer(RandomUtils.getFourBitRandom()).setLevel(1);
        return R.ok().data("code", teacherService.save(teacher));
    }

    @ApiOperation(value = "修改数据")
    @PostMapping("updateById")
    public R updateById(@ApiParam(value = "教师id",required = true) @RequestParam String id) {
        Teacher teacher = teacherService.getById(id);
        teacher.setId(id);
        teacher.setName(RandomUtils.getSixBitRandom());
        return R.ok().data("code", teacherService.updateById(teacher));
    }
}

