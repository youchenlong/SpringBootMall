package com.example.springbootmall.services;

import com.example.springbootmall.model.PmsBrand;

public interface PmsBrandService {
    int createBrand(PmsBrand pmsBrand);

    int updateBrand(Long id, PmsBrand pmsBrand);

    int deleteBrandById(Long id);

    PmsBrand selectBrandById(Long id);
}
