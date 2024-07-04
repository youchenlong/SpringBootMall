package com.example.springbootmall.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode
public class UmsUser implements Serializable {
    private Long id;
    @ApiModelProperty(value = "用户名")
    private String username;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "昵称")
    private String nickname;
    @ApiModelProperty(value = "电话号码")
    private String phone;
    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty(value = "性别：0->未知；1->男；2->女")
    private Integer gender;
    @ApiModelProperty(value = "创建时间：2024-05-21 17:33:01")
    private Date createTime;
    @ApiModelProperty(value = "生日")
    private Date birthday;
}
