package com.dxy.letterboxbackstage.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: JasonD
 * @date: 2023/5/10 00:15
 * @Description:
 */
@ApiModel("用户后端数据对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Integer id;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String phone;
    private String address;
    private String roleName;
    private String avatar;
    private Boolean enable;
    private String token;
}
