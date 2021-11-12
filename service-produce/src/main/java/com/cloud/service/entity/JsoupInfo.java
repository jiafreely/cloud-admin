package com.cloud.service.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.cloud.service.model.BaseEntity;
import com.cloud.service.util.LocalDateConverter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: XiaoK
 * @description: TODO
 * @date 2021/9/7 11:32
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("jsoup_info")
@Accessors(chain = true)
@ApiModel(value = "聚合爬取", description = "聚合爬取")
public class JsoupInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ExcelProperty("网站信息")
    @ApiModelProperty(value = "网站信息")
    @TableField("title_info")
    private String titleInfo;

    @ExcelProperty("文章名称")
    @ApiModelProperty(value = "文章名称")
    @TableField("title_name")
    private String titleName;

    @ExcelProperty("文章地址")
    @ApiModelProperty(value = "文章地址")
    private String url;

    @ExcelProperty(value = "文章上传时间", converter = LocalDateConverter.class)
    @ApiModelProperty(value = "文章上传时间")
    @TableField(value = "article_time")
    private LocalDate articleTime;

    /**
     * 忽略这个字段
     *
     * @ExcelIgnore
     */
    @ExcelProperty("版本")
    @ApiModelProperty(value = "版本")
    @Version
    private Integer version;

    @Override
    public String toString() {
        return "JsoupInfo{" +
                "id='" + this.getId() + '\'' +
                ", titleInfo='" + titleInfo + '\'' +
                ", titleName='" + titleName + '\'' +
                ", url='" + url + '\'' +
                ", articleTime=" + articleTime +
                ", version=" + version +
                ", gmtCreate=" + this.getGmtCreate() +
                ", gmtModified=" + this.getGmtModified() +
                '}';
    }
}
