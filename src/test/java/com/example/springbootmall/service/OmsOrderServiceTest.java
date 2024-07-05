package com.example.springbootmall.service;

import com.example.springbootmall.model.OmsOrder;
import com.example.springbootmall.model.OmsOrderItem;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
class OmsOrderServiceTest {

    private static final Logger log = LoggerFactory.getLogger(OmsOrderServiceTest.class);

    @Autowired
    private OmsOrderService omsOrderService;

    @Test
    void addOrder() {
        OmsOrder order = new OmsOrder();
        order.setUserId(1L);
        order.setStatus(0);
        order.setCreateTime(new Date());
        order.setPaymentTime(new Date());
        order.setDeliveryTime(new Date());
        order.setReceiveTime(new Date());
        order.setCommentTime(new Date());
        order.setUpdateTime(new Date());
        int result = omsOrderService.addOrder(order);
        log.info("addOrder, id={}, result={}", order.getId(), result);
    }

    @Test
    void updateOrder() {
        OmsOrder order = omsOrderService.getOrderById(1L);
        // do nothing
        int result = omsOrderService.updateOrder(order.getId(), order);
        log.info("updateOrder, id={}, result={}", order.getId(), result);
    }

    @Test
    void removeOrder() {
        int result = omsOrderService.removeOrder(1L);
        log.info("removeOrder, id={}, result={}", 1L, result);
    }

    @Test
    void getOrderById() {
        OmsOrder order = omsOrderService.getOrderById(1L);
        log.info("getOrderById, id={}, result={}", 1L, order);
    }

    @Test
    void getOrderByUserId() {
        List<OmsOrder> orders = omsOrderService.getOrderByUserId(1L);
        log.info("getOrderByUserId, userId={}, orders={}", 1L, orders);
    }

    @Test
    void getAllOrders() {
        List<OmsOrder> orders = omsOrderService.getAllOrders();
        log.info("getAllOrders, orders={}", orders);
    }

    @Test
    void addOrderItemToOrder() {
        OmsOrderItem omsOrderItem = new OmsOrderItem();
        omsOrderItem.setId(1L);
        omsOrderItem.setOrderId(1L);
        omsOrderItem.setProductId(1L);
        omsOrderItem.setProductQuantity(1);
        int result = omsOrderService.addOrderItemToOrder(omsOrderItem);
        log.info("addOrderItemToOrder, id={}, result={}", omsOrderItem.getId(), result);
    }

    @Test
    void updateOrderItemFromOrder() {
        OmsOrderItem omsOrderItem = omsOrderService.getOrderItemById(1L);
        // do nothing
        int result = omsOrderService.updateOrderItemFromOrder(omsOrderItem.getId(), omsOrderItem);
        log.info("updateOrderItemFromOrder, id={}, result={}", omsOrderItem.getId(), result);
    }

    @Test
    void removeOrderItemFromOrderById() {
        int result = omsOrderService.removeOrderItemFromOrderById(1L);
        log.info("removeOrderItemFromOrderById, id={}, result={}", 1L, result);
    }

    @Test
    void getOrderItemById() {
        OmsOrderItem omsOrderItem = omsOrderService.getOrderItemById(1L);
        log.info("getOrderItemById, id={}, result={}", 1L, omsOrderItem);
    }

    @Test
    void getOrderItemByOrderId() {
        List<OmsOrderItem> orderItems = omsOrderService.getOrderItemByOrderId(1L);
        log.info("getOrderItemByOrderId, orderItems={}", orderItems);
    }

    @Test
    void getOrderItemByUserId() {
        List<OmsOrderItem> orderItems = omsOrderService.getOrderItemByUserId(1L);
        log.info("getOrderItemByUserId, orderItems={}", orderItems);
    }
}