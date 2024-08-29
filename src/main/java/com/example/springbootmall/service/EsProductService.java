package com.example.springbootmall.service;

import com.example.springbootmall.domain.EsProduct;
import org.springframework.data.domain.Page;

public interface EsProductService {
    int importAll();
    void delete(Long id);
    void deleteAll();
    EsProduct create(Long id);
    Page<EsProduct> search(String keyword, Integer pageNum, Integer pageSize);
    // 商品复合查询
    Page<EsProduct> search(String keyword, Integer pageNum, Integer pageSize, Integer sort);
    // 根据商品id推荐相关商品
    Page<EsProduct> recommend(Long id, Integer pageNum, Integer pageSize);
}
