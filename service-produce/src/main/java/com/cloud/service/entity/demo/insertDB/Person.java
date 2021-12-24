package com.cloud.service.entity.demo.insertDB;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: Person
 * @description: mysql单表insert极限：已实现每秒插入8.5w条数据
 * @date 2021/12/23 16:45
 * @see {https://huangjie.blog.csdn.net/article/details/121745749}
 */
@Data
@Accessors(chain = true)
@TableName("person")
@ApiModel(value = "insertDB", description = "insertDB")
public class Person implements Serializable {
    private Long id;
    private String name;//姓名
    private Long phone;//电话
    private BigDecimal salary;//薪水
    private String company;//公司
    private Integer ifSingle;//是否单身
    private Integer sex;//性别
    private String address;//住址
    private String createUser;
    @ApiModelProperty(value = "创建时间", example = "2019-01-01 8:00:00")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    @ApiModelProperty(value = "更新时间", example = "2019-01-01 8:00:00")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;
}
