package com.example.springbootmall.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class OmsCartItem {
    private Long id;
    private Long cartId;
    private Long productId;
    @ApiModelProperty(value = "购买数量")
    private Integer productQuantity;
}
