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
 * 用户角色关系表
 * </p>
 *
 * @author dxy
 * @since 2023-05-21
 */
@Getter
@Setter
@TableName("t_user_role")
@ApiModel(value = "UserRole对象", description = "用户角色关系表")
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户角色主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("角色id")
    private Integer roleId;


}
