package com.example.springbootmall.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

@Data
@EqualsAndHashCode
public class PmsBrand implements Serializable {
    private Long id;
    @ApiModelProperty(value = "品牌名称")
    private String name;
    @ApiModelProperty(value = "首字母")
    private String firstLetter;
    @ApiModelProperty(value = "是否为品牌制造商：0->不是；1->是")
    private Integer factoryStatus;
    @ApiModelProperty(value = "品牌故事")
    private String brandStory;
}
