package com.dxy.letterboxbackstage.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: JasonD
 * @date: 2023/6/17 16:08
 * @Description:
 */
@ApiModel("轮播图前端数据对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BannerVO {
    private Integer id;
    private String name;
    private String detail;
    private String url;
    private String goTo;
}
