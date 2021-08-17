package com.cloud.service.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloud.service.entity.UcenterMember;
import com.cloud.service.result.R;
import com.cloud.service.service.UcenterMemberService;
import com.cloud.service.util.RandomUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author xjh
 * @since 2021-08-17
 */
@Slf4j
@Api(description = "个人")
@RestController
@RequestMapping("admin/consumers/ucenter-member")
public class UcenterMemberController {
    @Autowired
    private UcenterMemberService ucenterMemberService;

    @ApiOperation(value = "查询教师:分页")
    @GetMapping("queryPageList")
    public R queryPageList() {
        Page<UcenterMember> page = new Page<>(1, 2);
        IPage<UcenterMember> ucenterMemberIPage = ucenterMemberService.page(page);
        return R.ok().data("list", ucenterMemberIPage.getRecords());
    }

    @ApiOperation(value = "添加数据")
    @PostMapping("add")
    public R add() {
        UcenterMember ucenterMember = new UcenterMember().setOpenid(RandomUtils.getFourBitRandom()).setMobile(RandomUtils.getFourBitRandom()).setNickname(RandomUtils.getFourBitRandom()).setSex(1);
        return R.ok().data("code", ucenterMemberService.save(ucenterMember));
    }

    @ApiOperation(value = "修改数据")
    @PostMapping("updateById")
    public R updateById(@ApiParam(value = "个人id",required = true) @RequestParam String id) {
        UcenterMember ucenterMember = ucenterMemberService.getById(id);
        ucenterMember.setId(id);
        ucenterMember.setNickname(RandomUtils.getSixBitRandom());
        return R.ok().data("code", ucenterMemberService.updateById(ucenterMember));
    }
}

