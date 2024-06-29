package com.example.springbootmall.service;

import com.example.springbootmall.component.CommonResult;

public interface UmsUserService {
    CommonResult generateAuthCode(String telephone);

    CommonResult verifyAuthCode(String telephone, String authCode);
}
