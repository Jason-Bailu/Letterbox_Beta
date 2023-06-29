package com.dxy.letterboxbackstage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 信件表
 * </p>
 *
 * @author dxy
 * @since 2023-06-04
 */
@Getter
@Setter
@TableName("t_letter")
@ApiModel(value = "Letter对象", description = "信件表")
public class Letter implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("信件主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("信件作者")
    private String username;

    @ApiModelProperty("联系电话")
    private String callPhone;

    @ApiModelProperty("收件方")
    private String receiver;

    @ApiModelProperty("收件电话")
    private String receiverPhone;

    @ApiModelProperty("信件来源")
    private String fromAddress;

    @ApiModelProperty("信件地址")
    private String toAddress;

    @ApiModelProperty("邮编")
    private String postalCode;

    @ApiModelProperty("信件内容")
    private String context;

    @ApiModelProperty("下载地址")
    private String url;

    @ApiModelProperty("信纸ID")
    private Integer paperId;

    @ApiModelProperty("信封ID")
    private Integer coverId;

    @ApiModelProperty("墨水ID")
    private Integer inkId;

    @ApiModelProperty("订单ID")
    private Integer orderId;

    @ApiModelProperty("默认0未支付 1已支付 2未发出 3已发出 4已退回 5已收到")
    private Integer status;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("逻辑删除 0未删除 1已删除")
    private Boolean deleted;

    @ApiModelProperty("1启用 0禁用")
    private Boolean enable;

    @Override
    public String toString() {
        return "Letter{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", callPhone='" + callPhone + '\'' +
                ", receiver='" + receiver + '\'' +
                ", receiverPhone='" + receiverPhone + '\'' +
                ", fromAddress='" + fromAddress + '\'' +
                ", toAddress='" + toAddress + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", context='" + context + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
