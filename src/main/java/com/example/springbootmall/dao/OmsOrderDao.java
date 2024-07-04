package com.example.springbootmall.dao;

import com.example.springbootmall.model.OmsOrder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OmsOrderDao {
    int insert(OmsOrder record);

    int update(OmsOrder record);

    int deleteByPrimaryKey(Long id);

    OmsOrder selectByPrimaryKey(Long id);

    List<OmsOrder> selectAll();
}
