package com.dxy.letterboxbackstage.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: JasonD
 * @date: 2023/6/4 21:24
 * @Description:
 */
@ApiModel("信件后端数据对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LetterDTO {
    private String username;
    private String callPhone;
    private String receiver;
    private String receiverPhone;
    private String fromAddress;
    private String toAddress;
    private String postalCode;
    private String context;
    private Integer paperId;
    private Integer coverId;
    private Integer inkId;
    private Integer status;
}
