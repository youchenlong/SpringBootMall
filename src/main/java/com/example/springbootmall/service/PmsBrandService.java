package com.example.springbootmall.service;

import com.example.springbootmall.model.PmsBrand;

import java.util.List;

public interface PmsBrandService {
    int insertBrand(PmsBrand brand);

    int updateBrand(Long id, PmsBrand brand);

    int deleteBrandById(Long id);

    PmsBrand selectBrandById(Long id);

    List<PmsBrand> selectAllBrand();
}
