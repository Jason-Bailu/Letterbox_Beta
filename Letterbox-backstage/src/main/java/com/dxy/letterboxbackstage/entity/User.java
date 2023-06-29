package com.dxy.letterboxbackstage.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author dxy
 * @since 2023-05-09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_user")
@ApiModel(value = "User对象", description = "用户表")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("电话")
    private String phone;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("角色")
    private String roleName;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty("逻辑删除 0未删除 1已删除")
    @TableLogic
    private Boolean deleted;

    @ApiModelProperty("1 启用 0 禁用")
    private Boolean enable;
}
