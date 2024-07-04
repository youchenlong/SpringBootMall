package com.example.springbootmall.service;

import com.example.springbootmall.component.CommonResult;

public interface UmsUserService {
    CommonResult<String> generateAuthCode(String telephone);

    CommonResult<String> verifyAuthCode(String telephone, String authCode);
}
