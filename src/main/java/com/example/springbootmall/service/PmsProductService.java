package com.example.springbootmall.service;

import com.example.springbootmall.model.PmsProduct;

import java.util.List;

public interface PmsProductService {
    int addProduct(PmsProduct pmsProduct);
    int updateProduct(Long productId, PmsProduct pmsProduct);
    int removeProductById(Long productId);
    PmsProduct getProductById(Long productId);
    List<PmsProduct> getAllProduct();
    List<PmsProduct> getAllProductBySale();
    List<PmsProduct> simpleSearch(String keyword);
}
