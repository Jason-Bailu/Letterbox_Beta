package com.dxy.letterboxbackstage.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 轮播图表
 * </p>
 *
 * @author dxy
 * @since 2023-06-17
 */
@Getter
@Setter
@TableName("t_banner")
@ApiModel(value = "Banner对象", description = "轮播图表")
public class Banner implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("轮播图主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("轮播图名称")
    @TableField(value = "banner_name")
    private String name;

    @ApiModelProperty("轮播图描述")
    private String detail;

    @ApiModelProperty("轮播图路径")
    private String url;

    @ApiModelProperty("跳转链接")
    @TableField(value = "goto")
    private String goTo;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty("逻辑删除 0未删除 1已删除")
    @TableLogic
    private Boolean deleted;

    @ApiModelProperty("1启用 0禁用")
    private Boolean enable;


}
