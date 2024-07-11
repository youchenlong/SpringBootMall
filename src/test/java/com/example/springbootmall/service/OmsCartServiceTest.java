package com.example.springbootmall.service;

import com.example.springbootmall.model.OmsCart;
import com.example.springbootmall.model.OmsCartItem;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
class OmsCartServiceTest {

    private static final Logger log = LoggerFactory.getLogger(OmsCartServiceTest.class);
    @Autowired
    private OmsCartService omsCartService;

    @Test
    void addCart() {
        OmsCart omsCart = new OmsCart();
        omsCart.setUserId(1L);
        omsCart.setStatus(0);
        omsCart.setCreateTime(new Date());
        omsCart.setUpdateTime(new Date());
        int result = omsCartService.addCart(omsCart);
        log.info("add id={}, result={}", omsCart.getId(), result);
    }

    @Test
    void updateCart() {
        OmsCart omsCart = omsCartService.getCartById(1L);
        int result = omsCartService.updateCart(1L, omsCart);
        log.info("update id={}, result={}", 1L, result);
    }

    @Test
    void removeCartById() {
        int result = omsCartService.removeCartById(1L);
        log.info("remove id={}, result={}", 1L, result);
    }

    @Test
    void getCartById() {
        OmsCart omsCart = omsCartService.getCartById(1L);
        log.info("get id={}, result={}", 1L, omsCart);
    }

    @Test
    void getCartByUserId() {
        OmsCart omsCart = omsCartService.getCartByUserId(1L);
        log.info("get id={}, result={}", 1L, omsCart);
    }

    @Test
    void getAllCarts() {
        List<OmsCart> omsCarts = omsCartService.getAllCarts();
        for (OmsCart omsCart : omsCarts) {
            log.info("get id={}, result={}", omsCart.getId(), omsCart);
        }
    }

    @Test
    void addItemToCart() {
        OmsCartItem omsCartItem = new OmsCartItem();
        omsCartItem.setCartId(1L);
        omsCartItem.setProductId(1L);
        omsCartItem.setProductQuantity(1);
        int result = omsCartService.addCartItemToCart(omsCartItem);
        log.info("add id={}, result={}", omsCartItem.getId(), result);
    }

    @Test
    void updateItemFromCart() {
        OmsCartItem omsCartItem = omsCartService.getCartItemById(1L);
        // do nothing
        omsCartService.updateCartItemFromCart(1L, omsCartItem);
        log.info("update id={}, result={}", 1L, omsCartItem);
    }

    @Test
    void removeItemFromCartById() {
        int result = omsCartService.removeCartItemFromCartById(1L);
        log.info("remove id={}, result={}", 1L, result);
    }

    @Test
    void getCartItemById() {
        OmsCartItem omsCartItem = omsCartService.getCartItemById(1L);
        log.info("get id={}, result={}", 1L, omsCartItem);
    }

    @Test
    void getCartItemByCartId() {
        List<OmsCartItem> omsCartItems = omsCartService.getCartItemByCartId(1L);
        for (OmsCartItem omsCartItem : omsCartItems) {
            log.info("get id={}, result={}", omsCartItem.getId(), omsCartItem);
        }
    }

    @Test
    void getCartItemByUserId() {
        List<OmsCartItem> omsCartItems = omsCartService.getCartItemByUserId(1L);
        for (OmsCartItem omsCartItem : omsCartItems) {
            log.info("get id={}, result={}", omsCartItem.getId(), omsCartItem);
        }
    }
}