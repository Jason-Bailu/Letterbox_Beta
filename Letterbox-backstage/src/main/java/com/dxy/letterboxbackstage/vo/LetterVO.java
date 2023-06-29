package com.dxy.letterboxbackstage.vo;

import com.dxy.letterboxbackstage.entity.Good;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * @Author: JasonD
 * @date: 2023/6/4 12:11
 * @Description:
 */
@ApiModel("信件前端数据对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LetterVO {
    private Integer id;
    private LocalDate date;
    private String username;
    private String callPhone;
    private String receiver;
    private String receiverPhone;
    private String fromAddress;
    private String toAddress;
    private String postalCode;
    private String context;
    private String url;
    private Integer paperId;
    private Integer coverId;
    private Integer inkId;
    private Integer status;
    private List<GoodVO> goods;
}
