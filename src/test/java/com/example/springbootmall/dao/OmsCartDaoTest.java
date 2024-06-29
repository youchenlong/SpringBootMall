package com.example.springbootmall.dao;

import com.example.springbootmall.model.OmsCart;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OmsCartDaoTest {

    private static final Logger log = LoggerFactory.getLogger(OmsCartDaoTest.class);

    @Autowired
    private OmsCartDao omsCartDao;

    @Test
    void insert() {
        OmsCart omsCart = new OmsCart();
        omsCart.setUserId(1L);
        omsCart.setStatus(0);
        omsCart.setCreateTime(new Date());
        omsCart.setUpdateTime(new Date());
        int result = omsCartDao.insert(omsCart);
        log.info("insert id={}, result={}", omsCart.getId(), result);
    }

    @Test
    void update() {
        OmsCart omsCart = new OmsCart();
        omsCart.setId(1L);
        omsCart.setUserId(1L);
        omsCart.setStatus(0);
        omsCart.setCreateTime(new Date());
        omsCart.setUpdateTime(new Date());
        int result = omsCartDao.update(omsCart);
        log.info("update id={}, result={}", omsCart.getId(), result);
    }

    @Test
    void deleteByPrimaryKey() {
        int result = omsCartDao.deleteByPrimaryKey(1L);
        log.info("delete id={}, result={}", 1L, result);
    }

    @Test
    void selectByPrimaryKey() {
        OmsCart omsCart = omsCartDao.selectByPrimaryKey(1L);
        log.info("select id={}, result={}", omsCart.getId(), omsCart);
    }

    @Test
    void selectAll() {
        List<OmsCart> omsCarts = omsCartDao.selectAll();
        for(OmsCart omsCart : omsCarts){
            log.info("select id={}, result={}", omsCart.getId(), omsCart);
        }
    }
}