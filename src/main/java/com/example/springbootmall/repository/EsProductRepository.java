package com.example.springbootmall.repository;

import com.example.springbootmall.domain.EsProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EsProductRepository extends ElasticsearchRepository<EsProduct, Long> {
    Page<EsProduct> findByNameOrDescriptionOrKeywords(String name, String description, String keywords, Pageable pageable);
}
