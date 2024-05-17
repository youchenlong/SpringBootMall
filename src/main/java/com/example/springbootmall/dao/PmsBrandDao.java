package com.example.springbootmall.dao;

import com.example.springbootmall.model.PmsBrand;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PmsBrandDao {

    int insert(PmsBrand row);

    int update(PmsBrand row);

    int deleteByPrimaryKey(Long id);

    PmsBrand selectByPrimaryKey(Long id);

    List<PmsBrand> selectAll();
}
