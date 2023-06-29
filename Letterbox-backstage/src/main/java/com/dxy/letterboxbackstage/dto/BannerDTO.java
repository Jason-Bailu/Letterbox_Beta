package com.dxy.letterboxbackstage.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: JasonD
 * @date: 2023/6/17 16:10
 * @Description:
 */
@ApiModel("轮播图后端数据对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BannerDTO {
    private String name;
    private String detail;
    private String url;
    private String goTo;
}
