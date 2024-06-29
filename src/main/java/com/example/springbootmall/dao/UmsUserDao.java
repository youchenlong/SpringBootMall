package com.example.springbootmall.dao;

import com.example.springbootmall.model.UmsUser;

import java.util.List;

public interface UmsUserDao {
    int insert(UmsUser record);

    int update(UmsUser record);

    int deleteByPrimaryKey(Long id);

    UmsUser selectByPrimaryKey(Long id);

    List<UmsUser> selectAll();
}
