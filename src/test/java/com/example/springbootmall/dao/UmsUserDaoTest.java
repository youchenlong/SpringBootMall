package com.example.springbootmall.dao;

import com.example.springbootmall.model.UmsUser;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UmsUserDaoTest {

    private static final Logger log = LoggerFactory.getLogger(UmsUserDaoTest.class);

    @Autowired
    private UmsUserDao umsUserDao;

    @Test
    void insert() {
        UmsUser umsUser = new UmsUser();
        umsUser.setUsername("202224021001");
        umsUser.setPassword("202224021001");
        umsUser.setNickname("Alice");
        umsUser.setPhone("13096190001");
        umsUser.setEmail("2091838001@qq.com");
        umsUser.setGender(0);
        umsUser.setCreateTime(new Date());
        umsUser.setBirthday(new Date());
        int result = umsUserDao.insert(umsUser);
        log.info("insert id={}, result={}", umsUser.getId(), result);
    }

    @Test
    void update() {
        UmsUser umsUser = umsUserDao.selectByPrimaryKey(1L);
        // do nothing
        int result = umsUserDao.update(umsUser);
        log.info("update id={}, result={}", umsUser.getId(), result);
    }

    @Test
    void deleteByPrimaryKey() {
        int result = umsUserDao.deleteByPrimaryKey(1L);
        log.info("delete id={}, result={}", 1L, result);
    }

    @Test
    void selectByPrimaryKey() {
        UmsUser umsUser = umsUserDao.selectByPrimaryKey(1L);
        log.info("select id={}, result={}", umsUser.getId(), umsUser);
    }

    @Test
    void selectByUsername() {
        UmsUser umsUser = umsUserDao.selectByUsername("202224021001");
        log.info("select id={}, result={}", umsUser.getId(), umsUser);
    }

    @Test
    void selectAll() {
        List<UmsUser> umsUsers = umsUserDao.selectAll();
        for(UmsUser umsUser : umsUsers){
            log.info("select id={}, result={}", umsUser.getId(), umsUser);
        }
    }
}