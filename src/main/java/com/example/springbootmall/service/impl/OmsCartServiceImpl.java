package com.example.springbootmall.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import com.example.springbootmall.dao.OmsCartDao;
import com.example.springbootmall.dao.OmsCartItemDao;
import com.example.springbootmall.model.OmsCart;
import com.example.springbootmall.model.OmsCartItem;
import com.example.springbootmall.service.OmsCartService;
import com.example.springbootmall.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class OmsCartServiceImpl implements OmsCartService {

    private static final Logger log = LoggerFactory.getLogger(OmsCartServiceImpl.class);
    @Autowired
    private RedisService redisService;
    @Value("${redis.key.prefix.cart}")
    private String REDIS_KEY_PREFIX_CART;
    @Value("${redis.key.expire.cart}")
    private Long REDIS_KEY_EXPIRE_CART;

    @Autowired
    private OmsCartDao omsCartDao;
    @Autowired
    private OmsCartItemDao omsCartItemDao;

    private void freeRedis(OmsCart cart) {
        // delete instead of update
        List<Object> cartItemIds = redisService.lrange(REDIS_KEY_PREFIX_CART + "allCartItemIdsOfCartId:" + cart.getId(), 0, -1);
        for (Object cartItemId : cartItemIds){
            redisService.del(REDIS_KEY_PREFIX_CART + "cartItemId:" + cartItemId);
        }
        redisService.del(REDIS_KEY_PREFIX_CART + "cartId:" + cart.getId());
        redisService.del(REDIS_KEY_PREFIX_CART + "userId:" + cart.getUserId());
        redisService.del(REDIS_KEY_PREFIX_CART + "allCartItemIdsOfCartId:" + cart.getId());
        redisService.del(REDIS_KEY_PREFIX_CART + "allCartIds");
    }

    @Override
    public int addCart(OmsCart cart) {
        // if cart already exists
        List<OmsCart> carts = getAllCarts();
        for (OmsCart oldCart : carts) {
            if (oldCart.getUserId().equals(cart.getUserId())) {
                log.info("cart already exists");
                return 0;
            }
        }

        // Cache Aside Pattern
        int result = omsCartDao.insert(cart);
        if (result == 0){
            log.info("add cart failed");
            return 0;
        }
        freeRedis(cart);

        return result;
    }

    @Override
    public int updateCart(Long cartId, OmsCart cart) {
        // Cache Aside Pattern
        cart.setId(cartId);
        cart.setUpdateTime(new Date());
        int result = omsCartDao.update(cart);
        if (result == 0){
            log.info("update cart failed");
            return 0;
        }
        freeRedis(cart);

        return result;
    }

    @Override
    public int removeCartById(Long cartId) {
        // Cache Aside Pattern
        int result = omsCartDao.deleteByPrimaryKey(cartId);
        if (result == 0){
            log.info("remove cart failed");
            return 0;
        }
        freeRedis(getCartById(cartId));

        return result;
    }

    @Override
    public OmsCart getCartById(Long cartId) {
        // search in redis first
        Map<Object, Object> map = redisService.hGetAll(REDIS_KEY_PREFIX_CART + "cartId:" + cartId);
        OmsCart cart = BeanUtil.fillBeanWithMap(map, new OmsCart(), false);
        if (!map.isEmpty()){
            return cart;
        }

        // search in mysql
        cart = omsCartDao.selectByPrimaryKey(cartId);

        // store in redis
        if (cart != null) {
            redisService.hSetAll(REDIS_KEY_PREFIX_CART + "cartId:" + cartId, BeanUtil.beanToMap(cart));
            redisService.expire(REDIS_KEY_PREFIX_CART + "cartId:" + cartId, REDIS_KEY_EXPIRE_CART);
            redisService.set(REDIS_KEY_PREFIX_CART + "userId:" + cart.getUserId(), cartId);
            return cart;
        }
        return cart;
    }

    @Override
    public OmsCart getCartByUserId(Long userId) {
        Object cartId = redisService.get(REDIS_KEY_PREFIX_CART + "userId:" + userId);
        return getCartById(Convert.convert(Long.class, cartId));
    }

    @Override
    public List<OmsCart> getAllCarts() {
        // search in redis first
        List<Object> cartIds = redisService.lrange(REDIS_KEY_PREFIX_CART + "allCartIds", 0, -1);
        List<OmsCart> carts = new ArrayList<>();
        if (cartIds != null && !cartIds.isEmpty()) {
            for (Object cartId : cartIds) {
                OmsCart cart = getCartById((Long) cartId);
                carts.add(cart);
            }
            return carts;
        }

        // search in mysql
        carts = omsCartDao.selectAll();

        // store in redis
        if (carts != null && !carts.isEmpty()) {
            for (OmsCart cart : carts) {
                redisService.lpush(REDIS_KEY_PREFIX_CART + "allCartIds", cart.getId());
            }
        }
        return carts;
    }

    @Override
    public int addItemToCart(OmsCartItem cartItem) {
        // if product already exists
        Long productId = cartItem.getProductId();
        List<OmsCartItem> cartItems = getCartItemByCartId(cartItem.getCartId());
        for (OmsCartItem oldCartItem : cartItems) {
            if (oldCartItem.getProductId().equals(productId)) {
                cartItem.setProductQuantity(cartItem.getProductQuantity() + oldCartItem.getProductQuantity());
                return updateItemFromCart(cartItem.getId(), cartItem);
            }
        }

        // Cache Aside Pattern
        int result = omsCartItemDao.insert(cartItem);
        if (result == 0){
            log.info("add cartItem failed");
            return 0;
        }
        OmsCart cart = getCartById(cartItem.getCartId());
        updateCart(cart.getId(), cart);
        freeRedis(cart);

        return result;
    }

    @Override
    public int updateItemFromCart(Long cartItemId, OmsCartItem cartItem) {
        // Cache Aside Pattern
        cartItem.setId(cartItemId);
        int result = omsCartItemDao.update(cartItem);
        if (result == 0){
            log.info("update cartItem failed");
            return 0;
        }
        OmsCart cart = getCartById(cartItem.getCartId());
        updateCart(cart.getId(), cart);
        freeRedis(cart);

        return result;
    }

    @Override
    public int removeItemFromCartById(Long cartItemId) {
        // Cache Aside Pattern
        int result = omsCartItemDao.deleteByPrimaryKey(cartItemId);
        if (result == 0){
            log.info("remove cartItem failed");
            return 0;
        }
        OmsCartItem cartItem = getCartItemById(cartItemId);
        OmsCart cart = getCartById(cartItem.getCartId());
        updateCart(cart.getId(), cart);
        freeRedis(cart);

        return result;
    }

    @Override
    public OmsCartItem getCartItemById(Long cartItemId) {
        // search in redis first
        Map<Object, Object> map =  redisService.hGetAll(REDIS_KEY_PREFIX_CART + "cartItemId:" + cartItemId);
        OmsCartItem cartItem = BeanUtil.fillBeanWithMap(map, new OmsCartItem(), false);
        if (!map.isEmpty()) {
            return cartItem;
        }

        // search in mysql
        cartItem = omsCartItemDao.selectByPrimaryKey(cartItemId);

        // store in redis
        if (cartItem != null) {
            redisService.hSetAll(REDIS_KEY_PREFIX_CART + "cartItemId:" + cartItemId, BeanUtil.beanToMap(cartItem));
            redisService.expire(REDIS_KEY_PREFIX_CART + "cartItemId:" + cartItemId, REDIS_KEY_EXPIRE_CART);
            return cartItem;
        }
        return cartItem;
    }

    @Override
    public List<OmsCartItem> getCartItemByCartId(Long cartId) {
        // search in redis first
        List<Object> cartItemIds = redisService.lrange(REDIS_KEY_PREFIX_CART + "allCartItemIdsOfCartId:" + cartId, 0, -1);
        List<OmsCartItem> cartItems = new ArrayList<OmsCartItem>();
        if (cartItemIds != null && !cartItemIds.isEmpty()) {
            for (Object cartItemId : cartItemIds) {
                OmsCartItem omsCartItem = getCartItemById(Convert.convert(Long.class, cartItemId));
                if (omsCartItem != null) {
                    cartItems.add(omsCartItem);
                }
            }
        }

        // search in mysql
        cartItems = omsCartItemDao.selectByCartId(cartId);

        // store in redis
        if (cartItems != null && !cartItems.isEmpty()) {
            for (OmsCartItem cartItem : cartItems) {
                redisService.lpush(REDIS_KEY_PREFIX_CART + "allCartItemIdsOfCartId:" + cartId, cartItem.getId());
            }
        }
        return cartItems;
    }

    @Override
    public List<OmsCartItem> getCartItemByUserId(Long userId) {
        Object cartId = redisService.get(REDIS_KEY_PREFIX_CART + "userId:" + userId);
        return getCartItemByCartId(Convert.convert(Long.class, cartId));
    }
}
