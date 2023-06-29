package com.dxy.letterboxbackstage.vo;

import com.dxy.letterboxbackstage.entity.Menu;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: JasonD
 * @date: 2023/5/27 16:10
 * @Description:
 */
@ApiModel("菜单前端数据对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuVO {
    private Integer id;

    private String menuName;

    private String path;

    private String component;

    private String icon;

    private String description;

    private Integer pid;

    private List<Menu> children;
}
