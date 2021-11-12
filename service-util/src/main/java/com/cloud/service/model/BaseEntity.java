package com.cloud.service.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cloud.service.util.LocalDateTimeConverter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: BaseEntity
 * @description: TODO
 * @date 2021/6/17 10:57
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ExcelProperty(value = "创建时间", converter = LocalDateTimeConverter.class)
    @ApiModelProperty(value = "创建时间", example = "2019-01-01 8:00:00")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    @ExcelProperty(value = "更新时间", converter = LocalDateTimeConverter.class)
    @ApiModelProperty(value = "更新时间", example = "2019-01-01 8:00:00")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;
}
