package com.example.springbootmall.dao;

import com.example.springbootmall.model.OmsOrderItem;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OmsOrderItemDaoTest {

    private static final Logger log = LoggerFactory.getLogger(OmsOrderItemDaoTest.class);

    @Autowired
    private OmsOrderItemDao omsOrderItemDao;

    @Test
    void insert() {
        OmsOrderItem omsOrderItem = new OmsOrderItem();
        omsOrderItem.setOrderId(1L);
        omsOrderItem.setProductId(1L);
        omsOrderItem.setProductQuantity(1);
        int result = omsOrderItemDao.insert(omsOrderItem);
        log.info("insert, id={}, result={}", omsOrderItem.getId(), result);
    }

    @Test
    void update() {
        OmsOrderItem omsOrderItem = new OmsOrderItem();
        omsOrderItem.setId(1L);
        omsOrderItem.setOrderId(1L);
        omsOrderItem.setProductId(1L);
        omsOrderItem.setProductQuantity(1);
        int result = omsOrderItemDao.update(omsOrderItem);
        log.info("update, id={}, result={}", omsOrderItem.getId(), result);
    }

    @Test
    void deleteByPrimaryKey() {
        int result = omsOrderItemDao.deleteByPrimaryKey(1L);
        log.info("delete, id={}, result={}", 1L, result);
    }

    @Test
    void selectByPrimaryKey() {
        OmsOrderItem omsOrderItem = omsOrderItemDao.selectByPrimaryKey(1L);
        log.info("select, id={}, result={}", omsOrderItem.getId(), omsOrderItem);
    }

    @Test
    void selectAll() {
        List<OmsOrderItem> omsOrderItems = omsOrderItemDao.selectAll();
        for(OmsOrderItem omsOrderItem : omsOrderItems){
            log.info("select, id={}, result={}", omsOrderItem.getId(), omsOrderItem);
        }
    }
}