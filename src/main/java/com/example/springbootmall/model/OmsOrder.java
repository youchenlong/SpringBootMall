package com.example.springbootmall.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode
public class OmsOrder {
    private Long id;
    private Long userId;
    @ApiModelProperty(value = "订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单")
    private Integer status;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "支付时间")
    private Date paymentTime;
    @ApiModelProperty(value = "发货时间")
    private Date deliveryTime;
    @ApiModelProperty(value = "确认收货时间")
    private Date receiveTime;
    @ApiModelProperty(value = "评价时间")
    private Date commentTime;
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;
}
