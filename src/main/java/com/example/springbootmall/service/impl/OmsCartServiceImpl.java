package com.example.springbootmall.service.impl;

import com.example.springbootmall.dao.OmsCartDao;
import com.example.springbootmall.dao.OmsCartItemDao;
import com.example.springbootmall.model.OmsCart;
import com.example.springbootmall.model.OmsCartItem;
import com.example.springbootmall.service.OmsCartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class OmsCartServiceImpl implements OmsCartService {

    private static final Logger log = LoggerFactory.getLogger(OmsCartServiceImpl.class);

    @Autowired
    private OmsCartDao omsCartDao;
    @Autowired
    private OmsCartItemDao omsCartItemDao;

    @Override
    @Transactional
    public int addCart(OmsCart cart) {
        if (cart == null) {
            return 0;
        }

        // if cart already exists
        List<OmsCart> carts = getAllCarts();
        if (carts != null && !carts.isEmpty()) {
            for (OmsCart oldCart : carts) {
                if (oldCart.getUserId().equals(cart.getUserId())) {
                    log.info("cart already exists");
                    return 0;
                }
            }
        }

        return omsCartDao.insert(cart);
    }

    @Override
    @Transactional
    public int updateCart(Long cartId, OmsCart cart) {
        if (cart == null) {
            return 0;
        }

        cart.setId(cartId);
        cart.setUpdateTime(new Date());
        return omsCartDao.update(cart);
    }

    @Override
    @Transactional
    public int removeCartById(Long cartId) {

        return omsCartDao.deleteByPrimaryKey(cartId);
    }

    @Override
    public OmsCart getCartById(Long cartId) {

        // search in mysql
        return omsCartDao.selectByPrimaryKey(cartId);
    }

    @Override
    public OmsCart getCartByUserId(Long userId) {

        // search in mysql
        return omsCartDao.selectByUserId(userId);
    }

    @Override
    public List<OmsCart> getAllCarts() {

        // search in mysql
        return omsCartDao.selectAll();
    }

    @Override
    @Transactional
    public int addCartItemToCart(OmsCartItem cartItem) {
        if (cartItem == null) {
            return 0;
        }
        // if product already exists
        Long productId = cartItem.getProductId();
        List<OmsCartItem> cartItems = getCartItemByCartId(cartItem.getCartId());
        if (cartItems != null && !cartItems.isEmpty()) {
            for (OmsCartItem oldCartItem : cartItems) {
                if (oldCartItem.getProductId().equals(productId)) {
                    cartItem.setProductQuantity(cartItem.getProductQuantity() + oldCartItem.getProductQuantity());
                    cartItem.setId(oldCartItem.getId());
                    return updateCartItemFromCart(cartItem.getId(), cartItem);
                }
            }
        }

        int result = omsCartItemDao.insert(cartItem);
        OmsCart cart = getCartById(cartItem.getCartId());
        updateCart(cart.getId(), cart);
        return result;
    }

    @Override
    @Transactional
    public int updateCartItemFromCart(Long cartItemId, OmsCartItem cartItem) {
        if (cartItem == null) {
            return 0;
        }

        cartItem.setId(cartItemId);
        int result = omsCartItemDao.update(cartItem);
        OmsCart cart = getCartById(cartItem.getCartId());
        updateCart(cart.getId(), cart);
        return result;
    }

    @Override
    @Transactional
    public int removeCartItemFromCartById(Long cartItemId) {

        OmsCartItem cartItem = getCartItemById(cartItemId);
        if (cartItem == null) {
            return 0;
        }

        int result = omsCartItemDao.deleteByPrimaryKey(cartItemId);
        OmsCart cart = getCartById(cartItem.getCartId());
        updateCart(cart.getId(), cart);
        return result;
    }

    @Override
    public OmsCartItem getCartItemById(Long cartItemId) {

        // search in mysql
        return omsCartItemDao.selectByPrimaryKey(cartItemId);
    }

    @Override
    public List<OmsCartItem> getCartItemByCartId(Long cartId) {

        // search in mysql
        return omsCartItemDao.selectByCartId(cartId);
    }

    @Override
    public List<OmsCartItem> getCartItemByUserId(Long userId) {
        OmsCart cart = getCartByUserId(userId);
        if (cart != null) {
            return getCartItemByCartId(cart.getId());
        }
        return null;
    }
}
