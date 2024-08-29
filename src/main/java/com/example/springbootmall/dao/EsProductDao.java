package com.example.springbootmall.dao;

import com.example.springbootmall.domain.EsProduct;

import java.util.List;

public interface EsProductDao {
    EsProduct selectEsProductById(Long id);
    List<EsProduct> selectAllEsProduct();
}
