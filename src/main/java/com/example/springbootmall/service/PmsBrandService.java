package com.example.springbootmall.service;

import com.example.springbootmall.model.PmsBrand;

import java.util.List;

public interface PmsBrandService {
    int createBrand(PmsBrand pmsBrand);

    int updateBrand(Long id, PmsBrand pmsBrand);

    int deleteBrandById(Long id);

    PmsBrand selectBrandById(Long id);

    List<PmsBrand> selectAllBrand();
}
