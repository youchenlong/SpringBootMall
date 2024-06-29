package com.example.springbootmall.dao;

import com.example.springbootmall.model.OmsOrder;

import java.util.List;

public interface OmsOrderDao {
    int insert(OmsOrder record);

    int update(OmsOrder record);

    int deleteByPrimaryKey(Long id);

    OmsOrder selectByPrimaryKey(Long id);

    List<OmsOrder> selectAll();
}
