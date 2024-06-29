package com.example.springbootmall.nosql.elasticsearch.repository;

import com.example.springbootmall.nosql.elasticsearch.document.EsProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EsProductRepository extends ElasticsearchRepository<EsProduct, Long> {
    Page<EsProduct> findByName(String name, Pageable pageable);
    Page<EsProduct> findBySubTitle(String subTitle, Pageable pageable);
    Page<EsProduct> findByKeywords(String keywords, Pageable pageable);
    Page<EsProduct> findByNameOrSubTitleOrKeywords(String name, String keywords, String subTitle, Pageable pageable);
}
