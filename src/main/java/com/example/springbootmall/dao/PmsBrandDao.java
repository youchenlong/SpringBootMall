package com.example.springbootmall.dao;

import com.example.springbootmall.model.PmsBrand;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PmsBrandDao {

    int insert(PmsBrand row);

    int update(PmsBrand row);

    int deleteByPrimaryKey(Long id);

    PmsBrand selectByPrimaryKey(Long id);
}
