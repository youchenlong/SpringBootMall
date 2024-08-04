package com.example.springbootmall.service.impl;

import com.example.springbootmall.dao.OmsOrderDao;
import com.example.springbootmall.dao.OmsOrderItemDao;
import com.example.springbootmall.model.OmsOrder;
import com.example.springbootmall.model.OmsOrderItem;
import com.example.springbootmall.model.PmsProduct;
import com.example.springbootmall.service.OmsOrderService;
import com.example.springbootmall.service.PmsProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class OmsOrderServiceImpl implements OmsOrderService {

    private static final Logger log = LoggerFactory.getLogger(OmsOrderServiceImpl.class);

    @Autowired
    private OmsOrderDao omsOrderDao;
    @Autowired
    private OmsOrderItemDao omsOrderItemDao;
    @Autowired
    private PmsProductService pmsProductService;

    @Override
    @Transactional
    public int addOrder(OmsOrder order) {
        if (order == null) {
            return 0;
        }

        return omsOrderDao.insert(order);
    }

    @Override
    @Transactional
    public int updateOrder(Long orderId, OmsOrder order) {
        if (order == null) {
            return 0;
        }

        order.setId(orderId);
        order.setUpdateTime(new Date());
        return omsOrderDao.update(order);
    }

    @Override
    @Transactional
    public int removeOrder(Long orderId) {
        // pay attention to the difference between `removeOrder` and `cancelOrder`
        return omsOrderDao.deleteByPrimaryKey(orderId);
    }

    @Override
    public OmsOrder getOrderById(Long orderId) {

        // search in mysql
        return omsOrderDao.selectByPrimaryKey(orderId);
    }

    @Override
    public List<OmsOrder> getOrderByUserId(Long userId) {

        // search in mysql
        return omsOrderDao.selectByUserId(userId);
    }

    @Override
    public List<OmsOrder> getAllOrders() {

        // search in mysql
        return omsOrderDao.selectAll();
    }

    @Override
    @Transactional
    public int addOrderItemToOrder(OmsOrderItem orderItem) {
        if (orderItem == null) {
            return 0;
        }
        // if orderItem already exists
        Long productId = orderItem.getProductId();
        List<OmsOrderItem> orderItems = getOrderItemByOrderId(orderItem.getOrderId());
        if (orderItems != null && !orderItems.isEmpty()) {
            for (OmsOrderItem oldOrderItem : orderItems) {
                if (oldOrderItem.getProductId().equals(productId)) {
                    PmsProduct product = pmsProductService.getProductById(orderItem.getProductId());
                    product.setSale(product.getSale() + orderItem.getProductQuantity());
                    product.setStock(product.getStock() - orderItem.getProductQuantity());
                    pmsProductService.updateProduct(product.getId(), product);
                    orderItem.setProductQuantity(orderItem.getProductQuantity() + oldOrderItem.getProductQuantity());
                    orderItem.setId(oldOrderItem.getId());
                    return updateOrderItemFromOrder(orderItem.getId(), orderItem);
                }
            }
        }

        PmsProduct product = pmsProductService.getProductById(productId);
        product.setSale(product.getSale() + orderItem.getProductQuantity());
        product.setStock(product.getStock() - orderItem.getProductQuantity());
        pmsProductService.updateProduct(product.getId(), product);
        int result = omsOrderItemDao.insert(orderItem);
        OmsOrder order = getOrderById(orderItem.getOrderId());
        updateOrder(order.getId(), order);
        return result;
    }

    @Override
    @Transactional
    public int updateOrderItemFromOrder(Long orderItemId, OmsOrderItem orderItem) {
        if (orderItem == null) {
            return 0;
        }

        orderItem.setId(orderItemId);
        int result = omsOrderItemDao.update(orderItem);
        OmsOrder order = getOrderById(orderItem.getOrderId());
        updateOrder(order.getId(), order);
        return result;
    }

    @Override
    @Transactional
    public int removeOrderItemFromOrderById(Long orderItemId) {
        OmsOrderItem orderItem = getOrderItemById(orderItemId);
        if (orderItem == null) {
            return 0;
        }

        PmsProduct product = pmsProductService.getProductById(orderItem.getProductId());
        product.setSale(product.getSale() - orderItem.getProductQuantity());
        product.setStock(product.getStock() + orderItem.getProductQuantity());
        pmsProductService.updateProduct(product.getId(), product);
        int result = omsOrderItemDao.deleteByPrimaryKey(orderItemId);
        OmsOrder order = getOrderById(orderItem.getOrderId());
        updateOrder(order.getId(), order);
        return result;
    }

    @Override
    public OmsOrderItem getOrderItemById(Long orderItemId) {

        // search in mysql
        return omsOrderItemDao.selectByPrimaryKey(orderItemId);
    }

    @Override
    public List<OmsOrderItem> getOrderItemByOrderId(Long orderId) {

        // search in mysql
        return omsOrderItemDao.selectByOrderId(orderId);
    }

    @Override
    public List<OmsOrderItem> getOrderItemByUserId(Long userId) {
        List<OmsOrder> orders = getOrderByUserId(userId);
        if (orders != null && !orders.isEmpty()) {
            List<OmsOrderItem> orderItems = new ArrayList<>();
            for (OmsOrder order : orders) {
                List<OmsOrderItem> orderItem = getOrderItemByOrderId(order.getId());
                if (orderItem != null) {
                    orderItems.addAll(orderItem);
                }
            }
            return orderItems;
        }
        return null;
    }

    @Override
    @Transactional
    public int cancelOrder(OmsOrder order) {
        if (order == null) {
            return 0;
        }
        List<OmsOrderItem> orderItems = getOrderItemByOrderId(order.getId());
        if (orderItems != null && !orderItems.isEmpty()) {
            for (OmsOrderItem orderItem : orderItems) {
                if (removeOrderItemFromOrderById(orderItem.getId()) == 0) {
                    log.info("cancel order failed");
                    return 0;
                }
            }
        }
        return removeOrder(order.getId());
    }
}
