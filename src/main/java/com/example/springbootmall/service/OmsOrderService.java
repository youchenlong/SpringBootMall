package com.example.springbootmall.service;

import com.example.springbootmall.model.OmsOrder;
import com.example.springbootmall.model.OmsOrderItem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface OmsOrderService {
    @Transactional
    int addOrder(OmsOrder order);
    @Transactional
    int updateOrder(Long orderId, OmsOrder order);
    @Transactional
    int removeOrder(Long orderId);
    OmsOrder getOrderById(Long orderId);
    List<OmsOrder> getOrderByUserId(Long userId);
    List<OmsOrder> getAllOrders();
    int addOrderItemToOrder(OmsOrderItem orderItem);
    int updateOrderItemFromOrder(Long orderItemId, OmsOrderItem orderItem);
    int removeOrderItemFromOrderById(Long orderItemId);
    OmsOrderItem getOrderItemById(Long orderItemId);
    List<OmsOrderItem> getOrderItemByOrderId(Long orderId);
    List<OmsOrderItem> getOrderItemByUserId(Long userId);
    @Transactional
    int cancelOrder(OmsOrder order);
}
