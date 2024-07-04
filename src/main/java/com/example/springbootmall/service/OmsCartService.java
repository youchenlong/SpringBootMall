package com.example.springbootmall.service;

import com.example.springbootmall.model.OmsCart;
import com.example.springbootmall.model.OmsCartItem;

import java.util.List;

public interface OmsCartService {
    int addCart(OmsCart cart);
    int updateCart(Long cartId, OmsCart cart);
    int removeCartById(Long cartId);
    OmsCart getCartById(Long cartId);
    OmsCart getCartByUserId(Long userId);
    List<OmsCart> getAllCarts();
    int addItemToCart(OmsCartItem cartItem);
    int updateItemFromCart(Long cartItemId, OmsCartItem cartItem);
    int removeItemFromCartById(Long cartItemId);
    OmsCartItem getCartItemById(Long cartItemId);
    List<OmsCartItem> getCartItemByCartId(Long cartId);
    List<OmsCartItem> getCartItemByUserId(Long userId);
}
