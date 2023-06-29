package com.dxy.letterboxbackstage.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: JasonD
 * @date: 2023/5/15 15:39
 * @Description:
 */
@ApiModel("用户前端数据对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {
    private Integer id;
    private String username;
    private String nickname;
    private String email;
    private String phone;
    private String address;
    private String roleName;
    private String avatar;
    private Boolean enable;
}
