package com.dxy.letterboxbackstage.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: JasonD
 * @date: 2023/5/15 20:52
 * @Description:
 */
@ApiModel("图片前端数据对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileVO {
    private Integer id;
    private String fileName;
    private String type;
    private Long size;
    private String url;
    private Boolean enable;
}
