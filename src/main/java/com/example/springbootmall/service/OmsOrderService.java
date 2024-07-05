package com.example.springbootmall.service;

import com.example.springbootmall.model.OmsOrder;
import com.example.springbootmall.model.OmsOrderItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OmsOrderService {
    int addOrder(OmsOrder order);
    int updateOrder(Long orderId, OmsOrder order);
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
    int cancelOrder(OmsOrder order);
}
