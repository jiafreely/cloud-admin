package com.cloud.service.DTO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.cloud.service.model.BaseEntity;
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
@Accessors(chain = true)
@ApiModel(value="小k娱乐网", description="小k娱乐网")
public class JsoupKDTO extends BaseEntity {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "文章名称")
    @TableField("title_name")
    private String titleName;
    @ApiModelProperty(value = "文章地址")
    private String url;
    @ApiModelProperty(value = "文章上传时间")
    @TableField("article_time")
    private LocalDate articleTime;
}
