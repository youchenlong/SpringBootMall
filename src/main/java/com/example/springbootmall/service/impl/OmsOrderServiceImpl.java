package com.example.springbootmall.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import com.example.springbootmall.component.OrderDelayMessageProducer;
import com.example.springbootmall.dao.OmsOrderDao;
import com.example.springbootmall.dao.OmsOrderItemDao;
import com.example.springbootmall.model.OmsOrder;
import com.example.springbootmall.model.OmsOrderItem;
import com.example.springbootmall.model.PmsProduct;
import com.example.springbootmall.service.OmsOrderService;
import com.example.springbootmall.service.PmsProductService;
import com.example.springbootmall.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
    @Autowired
    private RedisService redisService;
    @Autowired
    private OrderDelayMessageProducer orderDelayMessageProducer;
    @Value("${redis.key.prefix.order}")
    private String REDIS_KEY_PREFIX_ORDER;
    @Value("${redis.key.expire.order}")
    private int REDIS_KEY_EXPIRE_ORDER;


    private void freeRedis(OmsOrder order) {
        if (order == null) {
            return;
        }
        // delete instead of update
        redisService.delAllByPrefix(REDIS_KEY_PREFIX_ORDER);
    }

    @Override
//    @Transactional
    public int addOrder(OmsOrder order) {
        if (order == null) {
            return 0;
        }
        // Cache Aside Pattern
        int result = omsOrderDao.insert(order);
        if (result == 0) {
            log.info("add order failed");
            return 0;
        }
        freeRedis(order);
        // order time out process
        orderDelayMessageProducer.sendMessage(String.valueOf(order.getId()));

        return result;
    }

    @Override
//    @Transactional
    public int updateOrder(Long orderId, OmsOrder order) {
        if (order == null) {
            return 0;
        }
        // Cache Aside Pattern
        order.setId(orderId);
        order.setUpdateTime(new Date());
        int result = omsOrderDao.update(order);
        if (result == 0) {
            log.info("update order failed");
            return 0;
        }
        freeRedis(order);

        return result;
    }

    @Override
//    @Transactional
    public int removeOrder(Long orderId) {
        // pay attention to the difference between `removeOrder` and `cancelOrder`
        // Cache Aside Pattern
        int result = omsOrderDao.deleteByPrimaryKey(orderId);
        if (result == 0) {
            log.info("remove order failed");
            return 0;
        }
        freeRedis(getOrderById(orderId));

        return result;
    }

    @Override
    public OmsOrder getOrderById(Long orderId) {
        // search in redis first
        Map<Object, Object> map = redisService.hGetAll(REDIS_KEY_PREFIX_ORDER + "orderId:" + orderId);
        OmsOrder order = BeanUtil.fillBeanWithMap(map, new OmsOrder(), false);
        if (!map.isEmpty()) {
            return order;
        }

        // search in mysql
        order = omsOrderDao.selectByPrimaryKey(orderId);

        // store in redis
        if (order != null) {
            redisService.hSetAll(REDIS_KEY_PREFIX_ORDER + "orderId:" + orderId, BeanUtil.beanToMap(order));
            redisService.expire(REDIS_KEY_PREFIX_ORDER + "orderId:" + orderId, REDIS_KEY_EXPIRE_ORDER);
            return order;
        }
        return null;
    }

    @Override
    public List<OmsOrder> getOrderByUserId(Long userId) {
        // search in redis first
        List<Object> orderIds = redisService.lrange(REDIS_KEY_PREFIX_ORDER + "userId:" + userId, 0, -1);
        List<OmsOrder> orders = new ArrayList<>();
        if (orderIds != null && !orderIds.isEmpty()) {
            for (Object orderId : orderIds) {
                OmsOrder order = getOrderById(Convert.convert(Long.class, orderId));
                orders.add(order);
            }
            return orders;
        }

        // search in mysql
        orders = omsOrderDao.selectByUserId(userId);

        // store in redis
        if (orders != null && !orders.isEmpty()) {
            for (OmsOrder order : orders) {
                redisService.lpush(REDIS_KEY_PREFIX_ORDER + "userId:" + userId, order.getId());
            }
            return orders;
        }
        return null;
    }

    @Override
    public List<OmsOrder> getAllOrders() {
        // search in redis first
        List<Object> orderIds = redisService.lrange(REDIS_KEY_PREFIX_ORDER + "allOrderIds", 0, -1);
        List<OmsOrder> orders = new ArrayList<>();
        if (orderIds != null && !orderIds.isEmpty()) {
            for (Object orderId : orderIds) {
                OmsOrder order = getOrderById(Convert.convert(Long.class, orderId));
                orders.add(order);
            }
            return orders;
        }

        // search in mysql
        orders = omsOrderDao.selectAll();

        // store in redis
        if (orders != null && !orders.isEmpty()) {
            for (OmsOrder order : orders) {
                redisService.lpush(REDIS_KEY_PREFIX_ORDER + "allOrderIds", order.getId());
            }
            return orders;
        }
        return null;
    }

    @Override
//    @Transactional
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

        // Cache Aside Pattern
        PmsProduct product = pmsProductService.getProductById(productId);
        product.setSale(product.getSale() + orderItem.getProductQuantity());
        product.setStock(product.getStock() - orderItem.getProductQuantity());
        pmsProductService.updateProduct(product.getId(), product);
        int result = omsOrderItemDao.insert(orderItem);
        if (result == 0) {
            log.info("add orderItem failed");
            return 0;
        }
        OmsOrder order = getOrderById(orderItem.getOrderId());
        updateOrder(order.getId(), order);
        freeRedis(order);
        return result;
    }

    @Override
//    @Transactional
    public int updateOrderItemFromOrder(Long orderItemId, OmsOrderItem orderItem) {
        if (orderItem == null) {
            return 0;
        }
        // Cache Aside Pattern
        orderItem.setId(orderItemId);
        int result = omsOrderItemDao.update(orderItem);
        if (result == 0) {
            log.info("update orderItem failed");
            return 0;
        }
        OmsOrder order = getOrderById(orderItem.getOrderId());
        updateOrder(order.getId(), order);
        freeRedis(order);
        return result;
    }

    @Override
//    @Transactional
    public int removeOrderItemFromOrderById(Long orderItemId) {
        OmsOrderItem orderItem = getOrderItemById(orderItemId);
        if (orderItem == null) {
            return 0;
        }
        // Cache Aside Pattern
        PmsProduct product = pmsProductService.getProductById(orderItem.getProductId());
        product.setSale(product.getSale() - orderItem.getProductQuantity());
        product.setStock(product.getStock() + orderItem.getProductQuantity());
        pmsProductService.updateProduct(product.getId(), product);
        int result = omsOrderItemDao.deleteByPrimaryKey(orderItemId);
        if (result == 0) {
            log.info("remove orderItem failed");
            return 0;
        }
        OmsOrder order = getOrderById(orderItem.getOrderId());
        updateOrder(order.getId(), order);
        freeRedis(order);
        return result;
    }

    @Override
    public OmsOrderItem getOrderItemById(Long orderItemId) {
        // search in redis first
        Map<Object, Object> map = redisService.hGetAll(REDIS_KEY_PREFIX_ORDER + "orderItemId:" + orderItemId);
        OmsOrderItem orderItem = BeanUtil.fillBeanWithMap(map, new OmsOrderItem(), false);
        if (!map.isEmpty()) {
            return orderItem;
        }

        // search in mysql
        orderItem = omsOrderItemDao.selectByPrimaryKey(orderItemId);

        // store in redis
        if (orderItem != null) {
            redisService.hSetAll(REDIS_KEY_PREFIX_ORDER + "orderItemId:" + orderItemId, BeanUtil.beanToMap(orderItem));
            redisService.expire(REDIS_KEY_PREFIX_ORDER + "orderItemId:" + orderItemId, REDIS_KEY_EXPIRE_ORDER);
            return orderItem;
        }
        return null;
    }

    @Override
    public List<OmsOrderItem> getOrderItemByOrderId(Long orderId) {
        // search in redis first
        List<Object> orderItemIds = redisService.lrange(REDIS_KEY_PREFIX_ORDER + "allOrderItemIdsOfOrderId:" + orderId, 0, -1);
        List<OmsOrderItem> orderItems = new ArrayList<>();
        if (orderItemIds != null && !orderItemIds.isEmpty()) {
            for (Object orderItemId : orderItemIds) {
                OmsOrderItem omsOrderItem = getOrderItemById(Convert.convert(Long.class, orderItemId));
                if (omsOrderItem != null) {
                    orderItems.add(omsOrderItem);
                }
            }
            return orderItems;
        }

        // search in mysql
        orderItems = omsOrderItemDao.selectByOrderId(orderId);

        // store in redis
        if (orderItems != null && !orderItems.isEmpty()) {
            for (OmsOrderItem orderItem : orderItems) {
                redisService.lpush(REDIS_KEY_PREFIX_ORDER + "allOrderItemIdsOfOrderId:" + orderId, orderItem.getId());
            }
            return orderItems;
        }
        return null;
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
//    @Transactional
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
        int result = removeOrder(order.getId());
        if (result == 0) {
            log.info("cancel order failed");
            return 0;
        }
        log.info("cancel order success");
        return result;
    }
}
