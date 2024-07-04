package com.example.springbootmall.controller;

import com.example.springbootmall.component.CommonResult;
import com.example.springbootmall.service.UmsUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "UmsUserController")
@Tag(name = "UmsUserController", description = "会员登录注册管理")
@RequestMapping("/member")
public class UmsUserController {
    @Autowired
    private UmsUserService umsMemberService;

    private static final Logger log = LoggerFactory.getLogger(UmsUserController.class);

}
