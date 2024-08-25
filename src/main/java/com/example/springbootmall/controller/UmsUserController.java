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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Api(tags = "UmsUserController")
@Tag(name = "UmsUserController", description = "用户登录注册管理")
@RequestMapping("/user")
public class UmsUserController {
    @Autowired
    private UmsUserService umsUserService;

    private static final Logger log = LoggerFactory.getLogger(UmsUserController.class);

    @ApiOperation("注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public CommonResult register(UmsUser umsUser) {
        UmsUser user = umsUserService.register(umsUser);
        if (user == null) {
            return CommonResult.failed("register failed");
        }
        return CommonResult.success(user);
    }

    @ApiOperation("登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public CommonResult login(@RequestParam("username") String username, @RequestParam("password") String password) {
        String token = umsUserService.login(username, password);
        if (token == null) {
            return CommonResult.failed("login failed");
        }
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        return CommonResult.success(map);
    }

}
