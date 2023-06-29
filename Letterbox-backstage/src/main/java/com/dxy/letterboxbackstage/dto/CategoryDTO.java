package com.dxy.letterboxbackstage.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: JasonD
 * @date: 2023/6/22 15:48
 * @Description:
 */
@ApiModel("分类后端数据对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    private String className;
    private String icon;
    private Integer count;
}
