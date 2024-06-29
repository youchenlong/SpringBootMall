package com.example.springbootmall.dao;

import com.example.springbootmall.model.OmsOrderItem;

import java.util.List;

public interface OmsOrderItemDao {
    int insert(OmsOrderItem record);

    int update(OmsOrderItem record);

    int deleteByPrimaryKey(Long id);

    OmsOrderItem selectByPrimaryKey(Long id);

    List<OmsOrderItem> selectAll();
}
