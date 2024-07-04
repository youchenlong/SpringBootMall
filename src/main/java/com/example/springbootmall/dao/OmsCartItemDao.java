package com.example.springbootmall.dao;

import com.example.springbootmall.model.OmsCartItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OmsCartItemDao {
    int insert(OmsCartItem record);

    int update(OmsCartItem record);

    int deleteByPrimaryKey(Long id);

    OmsCartItem selectByPrimaryKey(Long id);

    List<OmsCartItem> selectByCartId(Long cartId);

    List<OmsCartItem> selectAll();
}
