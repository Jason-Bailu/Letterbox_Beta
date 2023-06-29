package com.dxy.letterboxbackstage.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: JasonD
 * @date: 2023/6/22 16:41
 * @Description:
 */
@ApiModel("配件后端数据对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodDTO {
    private Integer id;
    private String goodName;
    private String detail;
    private String className;
    private String imageUrl;
    private Integer repository;
}
