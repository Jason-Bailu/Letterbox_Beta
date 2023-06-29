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
 * 文件表
 * </p>
 *
 * @author dxy
 * @since 2023-05-15
 */
@Getter
@Setter
@TableName("t_file")
@ApiModel(value = "File对象", description = "文件表")
public class File implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("文件主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("文件全称")
    private String fileName;

    @ApiModelProperty("文件类型")
    private String type;

    @ApiModelProperty("文件大小")
    private Long size;

    @ApiModelProperty("下载链接")
    private String url;

    @ApiModelProperty("文件内容md5")
    private String md5;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty("逻辑删除 0未删除 1已删除")
    @TableLogic
    private Boolean deleted;

    @ApiModelProperty("1启用 0禁用")
    private Boolean enable;


}
