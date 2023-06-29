package com.dxy.letterboxbackstage.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: JasonD
 * @date: 2023/5/15 20:52
 * @Description:
 */
@ApiModel("图片后端数据对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileDTO {
    private String fileName;
    private String type;
    private Long size;
    private String url;
    private String md5;
    private Boolean enable;
}
