package com.example.springbootmall.service.impl;

import com.example.springbootmall.component.CommonResult;
import com.example.springbootmall.service.RedisService;
import com.example.springbootmall.service.UmsMemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UmsMemberServiceImpl implements UmsMemberService {
    @Autowired
    private RedisService redisService;
    @Value("${redis.key.prefix.authCode}")
    private String REDIS_KEY_PREDIX_AUTH_CODE;
    @Value("${redis.key.expire.authCode}")
    private Long REDIS_KEY_EXPIRE_AUTH_CODE;

    private static final Logger log = LoggerFactory.getLogger(UmsMemberServiceImpl.class);

    @Override
    public CommonResult generateAuthCode(String telephone){
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }
        redisService.set(REDIS_KEY_PREDIX_AUTH_CODE + telephone, sb.toString());
        redisService.expire(REDIS_KEY_PREDIX_AUTH_CODE + telephone, REDIS_KEY_EXPIRE_AUTH_CODE);
        return CommonResult.success(sb.toString(), "auth code generated");
    }

    @Override
    public CommonResult verifyAuthCode(String telephone, String authCode){
        if(authCode == null){
            return CommonResult.failed("get auth code first");
        }
        String realAuthCode = (String)redisService.get(REDIS_KEY_PREDIX_AUTH_CODE + telephone);
        if(!authCode.equals(realAuthCode)){
            return CommonResult.failed("auth code not match");
        }
        else{
            return CommonResult.success(null, "auth code verified");
        }
    }
}
