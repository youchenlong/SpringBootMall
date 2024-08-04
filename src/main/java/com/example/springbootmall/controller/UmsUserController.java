package com.example.springbootmall.controller;

import com.example.springbootmall.component.CommonResult;
import com.example.springbootmall.model.UmsUser;
import com.example.springbootmall.service.UmsUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@Api(tags = "UmsUserController")
@Tag(name = "UmsUserController", description = "会员登录注册管理")
@RequestMapping("/user")
public class UmsUserController {
    @Autowired
    private UmsUserService umsUserService;

    private static final Logger log = LoggerFactory.getLogger(UmsUserController.class);

    @ApiOperation("账号密码登录")
    @RequestMapping(value = "/login/{username}/{password}", method = RequestMethod.GET)
    public CommonResult<UmsUser> login(@PathVariable("username") String username, @PathVariable("password") String password) {
        int count = umsUserService.login(username, password);
        if (count > 0) {
            return CommonResult.success(null);
        }
        else {
            return CommonResult.failed("failed");
        }
    }

    @ApiOperation("生成验证码")
    @RequestMapping(value = "/generateAuthCode/{telephone}", method = RequestMethod.GET)
    public CommonResult<String> generateAuthCode(@PathVariable String telephone, HttpSession session) {
        String authCode = umsUserService.generateAuthCode(telephone, session);
        if (authCode != null) {
            log.info("authCode: {}", authCode);
            return CommonResult.success(authCode);
        }
        else {
            return CommonResult.failed("failed");
        }
    }

    @ApiOperation("验证二维码")
    @RequestMapping(value = "/verifyAuthCode/{telephone}/{authCode}", method = RequestMethod.GET)
    public CommonResult<UmsUser> verifyAuthCode(@PathVariable String telephone, @PathVariable String authCode, HttpSession session) {
        int result = umsUserService.verifyAuthCode(telephone, authCode, session);
        if (result > 0) {
            log.info("success");
            return CommonResult.success(null);
        }
        else {
            log.info("failed");
            return CommonResult.failed("failed");
        }
    }
}
