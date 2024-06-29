package com.example.springbootmall.dao;

import com.example.springbootmall.model.OmsCart;

import java.util.List;

public interface OmsCartDao {
    int insert(OmsCart record);

    int update(OmsCart record);

    int deleteByPrimaryKey(Long id);

    OmsCart selectByPrimaryKey(Long id);

    List<OmsCart> selectAll();
}
