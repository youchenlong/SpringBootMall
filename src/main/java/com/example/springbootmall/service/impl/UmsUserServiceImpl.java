package com.example.springbootmall.service.impl;

import com.example.springbootmall.component.CommonResult;
import com.example.springbootmall.service.RedisService;
import com.example.springbootmall.service.UmsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UmsUserServiceImpl implements UmsUserService {
    @Autowired
    private RedisService redisService;
    @Value("${redis.key.prefix.auth}")
    private String REDIS_KEY_PREDIX_AUTH;
    @Value("${redis.key.expire.auth}")
    private Long REDIS_KEY_EXPIRE_AUTH;

    @Override
    public CommonResult<String> generateAuthCode(String telephone){
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }
        redisService.set(REDIS_KEY_PREDIX_AUTH + telephone, sb.toString());
        redisService.expire(REDIS_KEY_PREDIX_AUTH + telephone, REDIS_KEY_EXPIRE_AUTH);
        return CommonResult.success(sb.toString(), "auth code generated");
    }

    @Override
    public CommonResult<String> verifyAuthCode(String telephone, String authCode){
        if(authCode == null){
            return CommonResult.failed("get auth code first");
        }
        String realAuthCode = (String)redisService.get(REDIS_KEY_PREDIX_AUTH + telephone);
        if(!authCode.equals(realAuthCode)){
            return CommonResult.failed("auth code not match");
        }
        else{
            return CommonResult.success(null, "auth code verified");
        }
    }
}
