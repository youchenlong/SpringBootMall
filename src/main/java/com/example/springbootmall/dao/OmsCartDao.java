package com.example.springbootmall.dao;

import com.example.springbootmall.model.OmsCart;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OmsCartDao {
    int insert(OmsCart record);

    int update(OmsCart record);

    int deleteByPrimaryKey(Long id);

    OmsCart selectByPrimaryKey(Long id);

    OmsCart selectByUserId(Long userId);

    List<OmsCart> selectAll();
}
