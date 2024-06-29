package com.example.springbootmall.dao;

import com.example.springbootmall.model.OmsOrder;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OmsOrderDaoTest {

    private static final Logger log = LoggerFactory.getLogger(OmsOrderDaoTest.class);

    @Autowired
    private OmsOrderDao omsOrderDao;

    @Test
    void insert() {
        OmsOrder omsOrder = new OmsOrder();
        omsOrder.setUserId(1L);
        omsOrder.setStatus(0);
        omsOrder.setCreateTime(new Date());
        omsOrder.setPaymentTime(new Date());
        omsOrder.setDeliveryTime(new Date());
        omsOrder.setReceiveTime(new Date());
        omsOrder.setCommentTime(new Date());
        omsOrder.setUpdateTime(new Date());
        int result = omsOrderDao.insert(omsOrder);
        log.info("insert, id={}, result={}", omsOrder.getId(), result);
    }

    @Test
    void update() {
        OmsOrder omsOrder = new OmsOrder();
        omsOrder.setId(1L);
        omsOrder.setUserId(1L);
        omsOrder.setStatus(0);
        omsOrder.setCreateTime(new Date());
        omsOrder.setPaymentTime(new Date());
        omsOrder.setDeliveryTime(new Date());
        omsOrder.setReceiveTime(new Date());
        omsOrder.setCommentTime(new Date());
        omsOrder.setUpdateTime(new Date());
        int result = omsOrderDao.update(omsOrder);
        log.info("update, id={}, result={}", omsOrder.getId(), result);
    }

    @Test
    void deleteByPrimaryKey() {
        int result = omsOrderDao.deleteByPrimaryKey(1L);
        log.info("delete, id={}, result={}", 1L, result);
    }

    @Test
    void selectByPrimaryKey() {
        OmsOrder omsOrder = omsOrderDao.selectByPrimaryKey(1L);
        log.info("select, id={}, result={}", omsOrder.getId(), omsOrder);
    }

    @Test
    void selectAll() {
        List<OmsOrder> omsOrders = omsOrderDao.selectAll();
        for(OmsOrder omsOrder : omsOrders){
            log.info("select, id={}, result={}", omsOrder.getId(), omsOrder);
        }
    }
}