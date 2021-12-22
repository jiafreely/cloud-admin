package com.cloud.service.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: ExcelTemplate
 * @description: TODO
 * @date 2021/11/20 17:22
 */
@Data
@Accessors(chain = true)
public class ExcelTemplate {
    @ExcelProperty("用户序号(必填)")
    public Integer id;
    @ExcelProperty("短信接收号码")
    public String sms;
    @ExcelProperty("微信接收地址")
    public String wx;
    @ExcelProperty("APP会员号")
    public String app;
    @ExcelProperty("APP E行会员号")
    public String e;
    @ExcelProperty("邮箱接收地址")
    public String email;
}
