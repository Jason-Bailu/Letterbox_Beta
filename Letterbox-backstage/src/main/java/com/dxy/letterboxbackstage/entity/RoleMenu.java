package com.dxy.letterboxbackstage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 角色菜单关系表
 * </p>
 *
 * @author dxy
 * @since 2023-05-21
 */
@Getter
@Setter
@TableName("t_role_menu")
@ApiModel(value = "RoleMenu对象", description = "角色菜单关系表")
public class RoleMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("角色菜单主角")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("角色id")
    private Integer roleId;

    @ApiModelProperty("菜单id")
    private Integer menuId;

    public RoleMenu(Integer roleId, Integer menuId) {
        this.roleId = roleId;
        this.menuId = menuId;
    }
}
