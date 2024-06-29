package com.example.springbootmall.nosql.elasticsearch.document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode
@Document(indexName = "pms")
@Setting(shards = 1, replicas = 0)
public class EsProduct implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    private Long brandId;
    private Long categoryId;
    @Field(type = FieldType.Keyword)
    private String brandName;
    @Field(type = FieldType.Keyword)
    private String categoryName;
    @Field(type = FieldType.Keyword)
    private String productSn; // 货号
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String name;
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String subTitle;
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String keywords;
    private String pic; // 图片
    private Integer sort; // 排序
    private BigDecimal price; // 价格
    private Integer sale; // 销量
    private Integer stock; // 库存
}
