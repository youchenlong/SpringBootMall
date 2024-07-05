package com.example.springbootmall.dao;

import com.example.springbootmall.model.OmsOrderItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OmsOrderItemDao {
    int insert(OmsOrderItem record);

    int update(OmsOrderItem record);

    int deleteByPrimaryKey(Long id);

    OmsOrderItem selectByPrimaryKey(Long id);

    List<OmsOrderItem> selectByOrderId(Long orderId);

    List<OmsOrderItem> selectAll();
}
