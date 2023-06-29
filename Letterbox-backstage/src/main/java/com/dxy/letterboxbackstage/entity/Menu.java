package com.dxy.letterboxbackstage.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author dxy
 * @since 2023-05-21
 */
@Getter
@Setter
@TableName("t_menu")
@ApiModel(value = "Menu对象", description = "菜单表")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("菜单名")
    private String menuName;

    @ApiModelProperty("访问路径")
    private String path;

    @ApiModelProperty("组件路径")
    private String component;

    @ApiModelProperty("访问权限")
    private String perms;

    @ApiModelProperty("图标")
    private String icon;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("父级id")
    private Integer pid;

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

    @ApiModelProperty("子集菜单")
    @TableField(exist = false)
    private List<Menu> children;

}
