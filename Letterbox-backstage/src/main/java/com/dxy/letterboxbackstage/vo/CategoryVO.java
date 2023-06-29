package com.dxy.letterboxbackstage.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: JasonD
 * @date: 2023/6/22 15:46
 * @Description:
 */
@ApiModel("分类前端数据对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryVO {
    private Integer id;
    private String className;
    private String icon;
    private Integer count;
}
