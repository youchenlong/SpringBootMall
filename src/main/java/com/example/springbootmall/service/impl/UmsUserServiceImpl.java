package com.example.springbootmall.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.example.springbootmall.dao.UmsUserDao;
import com.example.springbootmall.model.UmsUser;
import com.example.springbootmall.service.UmsUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class UmsUserServiceImpl implements UmsUserService {

    private static final Logger log = LoggerFactory.getLogger(UmsUserServiceImpl.class);

    @Autowired
    private UmsUserDao umsUserDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public int register(UmsUser user) {
        if (user == null) {
            return 0;
        }
        // if user already exists
        List<UmsUser> users =  getAllUser();
        if (users != null && !users.isEmpty()) {
            for (UmsUser oldUser : users) {
                if (oldUser.getUsername().equals(user.getUsername())) {
                    log.info("user already exist");
                    return 0;
                }
            }
        }

        // password encode
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        return umsUserDao.insert(user);
    }

    @Override
    @Transactional
    public int update(Long userId, UmsUser user) {
        if (user == null) {
            return 0;
        }
        user.setId(userId);
        return umsUserDao.update(user);
    }

    @Override
    @Transactional
    public int delete(Long userId) {
        return umsUserDao.deleteByPrimaryKey(userId);
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
    public String generateAuthCode(String telephone, HttpSession session){

        String authCode = RandomUtil.randomNumbers(6);
        session.setAttribute("telephone", telephone);
        session.setAttribute("authCode", authCode);
        return authCode;
    }

    @Override
    public int verifyAuthCode(String telephone, String authCode, HttpSession session){
        // authCode is empty
        if (authCode == null) {
            log.info("auth code is empty");
            return 0;
        }
        // realAuthCode expires or not exists
        String realTelephone = session.getAttribute("telephone").toString();
        String realAuthCode = session.getAttribute("authCode").toString();
        if (realAuthCode == null){
            log.info("generate auth code first");
            return 0;
        }
        // authCode not match
        if(!authCode.equals(realAuthCode) || !telephone.equals(realTelephone)){
            log.info("auth code is not correct");
            return 0;
        }
        return 1;
    }
}
