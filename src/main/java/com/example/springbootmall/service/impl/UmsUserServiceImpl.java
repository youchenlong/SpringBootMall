package com.example.springbootmall.service.impl;

import com.example.springbootmall.component.CommonResult;
import com.example.springbootmall.component.JwtUtils;
import com.example.springbootmall.dao.UmsUserDao;
import com.example.springbootmall.domain.AdminUserDetails;
import com.example.springbootmall.model.UmsUser;
import com.example.springbootmall.service.RedisService;
import com.example.springbootmall.service.UmsUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.remote.JMXAuthenticator;
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
    private JwtUtils jwtUtils;
    @Autowired
    private RedisService redisService;
    @Value("${redis.key.prefix.auth}")
    private String REDIS_KEY_PREFIX_AUTH;
    @Value("${redis.key.expire.auth}")
    private Long REDIS_KEY_EXPIRE_AUTH;

    @Override
    @Transactional
    public UmsUser register(UmsUser user) {
        // if user already exists
        List<UmsUser> users =  getAllUser();
        if (users != null && !users.isEmpty()) {
            for (UmsUser oldUser : users) {
                if (oldUser.getUsername().equals(user.getUsername())) {
                    log.info("user already exist");
                    return null;
                }
            }
        }

        // password encode
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        int result = umsUserDao.insert(user);
        if (result == 0) {
            log.info("register failed");
            return null;
        }
        return user;
    }

    @Override
    public String login(String username, String password) {
        UmsUser user = getUserByUsername(username);
        UserDetails userDetails = new AdminUserDetails(user);
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            return null;
        }
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtils.generateToken(userDetails);
        return token;
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

    @Override
    @Transactional
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
    @Transactional
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
}
