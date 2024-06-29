package com.example.springbootmall.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode
public class PmsProduct implements Serializable {
    private Long id;
    private Long brandId;
    @ApiModelProperty(value = "产品名称")
    private String name;
    @ApiModelProperty(value = "产品描述")
    private String description;
    @ApiModelProperty(value = "关键词")
    private String keywords;
    @ApiModelProperty(value = "价格")
    private BigDecimal price;
    @ApiModelProperty(value = "销量")
    private Integer sale;
    @ApiModelProperty(value = "库存")
    private Integer stock;
}
