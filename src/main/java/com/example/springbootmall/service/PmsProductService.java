package com.example.springbootmall.service;

import com.example.springbootmall.model.PmsProduct;

import java.util.List;

public interface PmsProductService {
    int insertProduct(PmsProduct pmsProduct);
    int updateProduct(Long id, PmsProduct pmsProduct);
    int deleteProductById(Long id);
    PmsProduct selectProductById(Long id);
    List<PmsProduct> selectAllProduct();
}
