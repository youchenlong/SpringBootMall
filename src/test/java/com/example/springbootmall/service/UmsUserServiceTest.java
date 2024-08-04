package com.example.springbootmall.service;

import com.example.springbootmall.model.UmsUser;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UmsUserServiceTest {

    private static final Logger log = LoggerFactory.getLogger(UmsUserServiceTest.class);
    @Autowired
    private UmsUserService umsUserService;

    @Test
    void register() {
        UmsUser umsUser = new UmsUser();
        umsUser.setUsername("202224021001");
        umsUser.setPassword("202224021001");
        umsUser.setNickname("Alice");
        umsUser.setPhone("13096190001");
        umsUser.setEmail("2091838001@qq.com");
        umsUser.setGender(0);
        umsUser.setCreateTime(new Date());
        umsUser.setBirthday(new Date());
        int result = umsUserService.register(umsUser);
        log.info("register id={}, result={}", umsUser.getId(), result);

    }

    @Test
    void update() {
        UmsUser umsUser = umsUserService.getUserById(1L);
        // do nothing
        int result = umsUserService.update(1L, umsUser);
        log.info("update id={}, result={}", umsUser.getId(), result);
    }

    @Test
    void delete() {
        int result = umsUserService.delete(1L);
        log.info("delete id={}", result);
    }

    @Test
    void getUserById() {
        UmsUser umsUser = umsUserService.getUserById(1L);
        log.info("getUserById user={}", umsUser);
    }

    @Test
    void getUserByUsername() {
        UmsUser umsUser = umsUserService.getUserByUsername("202224021001");
        log.info("getUserByUsername user={}", umsUser);
    }

    @Test
    void getAllUser() {
        List<UmsUser> umsUsers = umsUserService.getAllUser();
        for (UmsUser umsUser : umsUsers) {
            log.info("getAllUser user={}", umsUser);
        }
    }

    @Test
    void login() {
        String username = "202224021001";
        String password = "202224021001";
        int result = umsUserService.login(username, password);
        log.info("login id={}, result={}", username, result);
    }
}