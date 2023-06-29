package com.dxy.letterboxbackstage.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: JasonD
 * @date: 2023/6/22 16:43
 * @Description:
 */
@ApiModel("配件前端数据对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodVO {
    private Integer id;
    private String goodName;
    private String detail;
    private String className;
    private String imageUrl;
    private Integer repository;
}
