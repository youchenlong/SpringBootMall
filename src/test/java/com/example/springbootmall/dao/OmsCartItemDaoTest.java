package com.example.springbootmall.dao;

import com.example.springbootmall.model.OmsCart;
import com.example.springbootmall.model.OmsCartItem;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OmsCartItemDaoTest {

    private static final Logger log = LoggerFactory.getLogger(OmsCartItemDaoTest.class);

    @Autowired
    private OmsCartItemDao omsCartItemDao;

    @Test
    void insert() {
        OmsCartItem omsCartItem = new OmsCartItem();
        omsCartItem.setCartId(1L);
        omsCartItem.setProductId(1L);
        omsCartItem.setProductQuantity(1);
        int result = omsCartItemDao.insert(omsCartItem);
        log.info("insert, id={}, result={}", omsCartItem.getId(), result);
    }

    @Test
    void update() {
        OmsCartItem omsCartItem = new OmsCartItem();
        omsCartItem.setId(1L);
        omsCartItem.setCartId(1L);
        omsCartItem.setProductId(1L);
        omsCartItem.setProductQuantity(1);
        int result = omsCartItemDao.update(omsCartItem);
        log.info("update, id={}, result={}", omsCartItem.getId(), result);
    }

    @Test
    void deleteByPrimaryKey() {
        int result = omsCartItemDao.deleteByPrimaryKey(1L);
        log.info("delete, id={}, result={}", 1L, result);
    }

    @Test
    void selectByPrimaryKey() {
        OmsCartItem omsCartItem = omsCartItemDao.selectByPrimaryKey(1L);
        log.info("select, id={}, result={}", omsCartItem.getId(), omsCartItem);
    }

    @Test
    void selectAll() {
        List<OmsCartItem> omsCartItems = omsCartItemDao.selectAll();
        for(OmsCartItem omsCartItem : omsCartItems) {
            log.info("select, id={}, result={}", omsCartItem.getId(), omsCartItem);
        }
    }
}