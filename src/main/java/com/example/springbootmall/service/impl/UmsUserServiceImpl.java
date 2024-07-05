package com.example.springbootmall.service.impl;

import com.example.springbootmall.component.CommonResult;
import com.example.springbootmall.dao.UmsUserDao;
import com.example.springbootmall.model.UmsUser;
import com.example.springbootmall.service.RedisService;
import com.example.springbootmall.service.UmsUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class UmsUserServiceImpl implements UmsUserService {

    private static final Logger log = LoggerFactory.getLogger(UmsUserServiceImpl.class);

    @Autowired
    private UmsUserDao umsUserDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RedisService redisService;
    @Value("${redis.key.prefix.auth}")
    private String REDIS_KEY_PREFIX_AUTH;
    @Value("${redis.key.expire.auth}")
    private Long REDIS_KEY_EXPIRE_AUTH;

    @Override
    public int register(UmsUser user) {
        if (user == null) {
            return 0;
        }
        // if user already exists
        List<UmsUser> users =  getAllUser();
        for (UmsUser oldUser : users) {
            if (oldUser.getUsername().equals(user.getUsername())) {
                log.info("user already exist");
                return 0;
            }
        }

        // password encode
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        int result = umsUserDao.insert(user);
        if (result == 0) {
            log.info("register failed");
            return 0;
        }

        return result;
    }

    @Override
    public int update(Long userId, UmsUser user) {
        if (user == null) {
            return 0;
        }
        user.setId(userId);
        int result = umsUserDao.update(user);
        if (result == 0) {
            log.info("update failed");
            return 0;
        }

        return result;
    }

    @Override
    public int delete(Long userId) {
        int result = umsUserDao.deleteByPrimaryKey(userId);
        if (result == 0) {
            log.info("delete failed");
            return 0;
        }

        return result;
    }

    @Override
    public UmsUser getUserById(Long userId) {
        return umsUserDao.selectByPrimaryKey(userId);
    }

    @Override
    public UmsUser getUserByUsername(String username) {
        return umsUserDao.selectByUsername(username);
    }

    @Override
    public List<UmsUser> getAllUser() {
        return umsUserDao.selectAll();
    }

    @Override
    public int login(String username, String password) {
        UmsUser user = getUserByUsername(username);
        // if user not exists
        if (user == null) {
            log.info("user not found");
            return 0;
        }
        // if password is not correct
        if (!passwordEncoder.matches(password, user.getPassword())) {
            log.info("password is not correct");
            return 0;
        }
        return 1;
    }

    @Override
    public String generateAuthCode(String telephone){
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }
        redisService.set(REDIS_KEY_PREFIX_AUTH + telephone, sb.toString());
        redisService.expire(REDIS_KEY_PREFIX_AUTH + telephone, REDIS_KEY_EXPIRE_AUTH);
        return sb.toString();
    }

    @Override
    public int verifyAuthCode(String telephone, String authCode){
        // authCode is empty
        if (authCode == null) {
            log.info("auth code is empty");
            return 0;
        }
        // realAuthCode expires or not exists
        String realAuthCode = (String)redisService.get(REDIS_KEY_PREFIX_AUTH + telephone);
        if (realAuthCode == null){
            log.info("generate auth code first");
            return 0;
        }
        // authCode not match
        if(!authCode.equals(realAuthCode)){
            log.info("auth code is not correct");
            return 0;
        }
        return 1;
    }
}
