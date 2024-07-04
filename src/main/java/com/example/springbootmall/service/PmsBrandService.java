package com.example.springbootmall.service;

import com.example.springbootmall.model.PmsBrand;

import java.util.List;

public interface PmsBrandService {
    int addBrand(PmsBrand brand);

    int updateBrand(Long brandId, PmsBrand brand);

    int removeBrandById(Long brandId);

    PmsBrand getBrandById(Long brandId);

    List<PmsBrand> getAllBrand();
}
