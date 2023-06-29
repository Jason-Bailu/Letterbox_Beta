package com.dxy.letterboxbackstage.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 分类表
 * </p>
 *
 * @author dxy
 * @since 2023-06-22
 */
@Getter
@Setter
@TableName("t_category")
@ApiModel(value = "Category对象", description = "分类表")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("分类主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("分类名称")
    private String className;

    @ApiModelProperty("图标")
    private String icon;

    @ApiModelProperty("分类总量")
    private Integer count;

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
