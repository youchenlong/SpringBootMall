package com.example.springbootmall.dao;

import com.example.springbootmall.model.PmsProduct;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PmsProductDao {
    int insert(PmsProduct record);
    int update(PmsProduct record);
    int deleteByPrimaryKey(Long id);
    PmsProduct selectByPrimaryKey(Long id);
    List<PmsProduct> selectAll();
}
