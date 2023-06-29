package com.dxy.letterboxbackstage.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 信件配件表
 * </p>
 *
 * @author dxy
 * @since 2023-06-22
 */
@Getter
@Setter
@TableName("t_good")
@ApiModel(value = "Good对象", description = "信件配件表")
public class Good implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("商品主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("商品名称")
    private String goodName;

    @ApiModelProperty("商品描述")
    private String detail;

    @ApiModelProperty("商品类别")
    private String className;

    @ApiModelProperty("商品图片")
    private String imageUrl;

    @ApiModelProperty("商品价格")
    private Integer price;

    @ApiModelProperty("计量单位")
    private String unit;

    @ApiModelProperty("商品库存")
    private Integer repository;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty("逻辑删除 0未删除 1已删除")
    @TableLogic
    private Boolean deleted;

    @ApiModelProperty("1上架 0下架")
    private Boolean enable;


}
