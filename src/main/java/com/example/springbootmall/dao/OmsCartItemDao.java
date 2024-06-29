package com.example.springbootmall.dao;

import com.example.springbootmall.model.OmsCartItem;

import java.util.List;

public interface OmsCartItemDao {
    int insert(OmsCartItem record);

    int update(OmsCartItem record);

    int deleteByPrimaryKey(Long id);

    OmsCartItem selectByPrimaryKey(Long id);

    List<OmsCartItem> selectAll();
}
