package com.example.springbootmall.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.example.springbootmall.dao.EsProductDao;
import com.example.springbootmall.domain.EsProduct;
import com.example.springbootmall.repository.EsProductRepository;
import com.example.springbootmall.service.EsProductService;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EsProductServiceImpl implements EsProductService {
    @Autowired
    private EsProductDao esProductDao;
    @Autowired
    private EsProductRepository esProductRepository;
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public int importAll() {
        List<EsProduct> products = esProductDao.selectAllEsProduct();
        Iterable<EsProduct> iterable = esProductRepository.saveAll(products);
        Iterator<EsProduct> iterator = iterable.iterator();
        int result = 0;
        while (iterator.hasNext()) {
            result++;
            iterator.next();
        }
        return result;
    }
    @Override
    public void deleteAll() {
        esProductRepository.deleteAll();
    }

    @Override
    public void delete(Long id) {
        esProductRepository.deleteById(id);
    }

    @Override
    public EsProduct create(Long id) {
        EsProduct product = esProductDao.selectEsProductById(id);
        return esProductRepository.save(product);
    }

    @Override
    public Page<EsProduct> search(String keyword, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return esProductRepository.findByNameOrDescriptionOrKeywords(keyword, keyword, keyword, pageable);
    }

    @Override
    public Page<EsProduct> search(String keyword, Integer pageNum, Integer pageSize, Integer sort) {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        // 分页
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        nativeSearchQueryBuilder.withPageable(pageable);
        // 搜索
        FunctionScoreQueryBuilder.FilterFunctionBuilder[] filterFunctionBuilders = new FunctionScoreQueryBuilder.FilterFunctionBuilder[3];
        filterFunctionBuilders[0] = new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("name", keyword),
                ScoreFunctionBuilders.weightFactorFunction(10));
        filterFunctionBuilders[1] = new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("description", keyword),
                ScoreFunctionBuilders.weightFactorFunction(5));
        filterFunctionBuilders[2] = new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("keywords", keyword),
                ScoreFunctionBuilders.weightFactorFunction(2));
        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(filterFunctionBuilders)
                .scoreMode(FunctionScoreQuery.ScoreMode.SUM)
                .setMinScore(2);
        nativeSearchQueryBuilder.withQuery(functionScoreQueryBuilder);
        // 排序
        if (sort == 1) {
            nativeSearchQueryBuilder.withSorts(SortBuilders.fieldSort("price").order(SortOrder.ASC));
        }
        else if (sort == 2) {
            nativeSearchQueryBuilder.withSorts(SortBuilders.fieldSort("sale").order(SortOrder.DESC));
        }
        else if (sort == 3) {
            nativeSearchQueryBuilder.withSorts(SortBuilders.fieldSort("stock").order(SortOrder.DESC));
        }
        nativeSearchQueryBuilder.withSorts(SortBuilders.scoreSort().order(SortOrder.DESC));
        NativeSearchQuery searchQuery = nativeSearchQueryBuilder.build();
        SearchHits<EsProduct> searchHits = elasticsearchRestTemplate.search(searchQuery, EsProduct.class);
        if (searchHits.getTotalHits() <= 0) {
            return new PageImpl<>(ListUtil.empty(), pageable, 0);
        }
        List<EsProduct> products = searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
        return new PageImpl<>(products, pageable, searchHits.getTotalHits());
    }

    @Override
    public Page<EsProduct> recommend(Long id, Integer pageNum, Integer pageSize) {
        EsProduct product = esProductDao.selectEsProductById(id);
        if (product == null) {
            return new PageImpl<>(ListUtil.empty());
        }
        String keyword = product.getName();
        Long brandId = product.getBrandId();
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        // 分页
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        nativeSearchQueryBuilder.withPageable(pageable);
        // 搜索相关商品
        FunctionScoreQueryBuilder.FilterFunctionBuilder[] filterFunctionBuilders = new FunctionScoreQueryBuilder.FilterFunctionBuilder[4];
        filterFunctionBuilders[0] = new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("name", keyword),
                ScoreFunctionBuilders.weightFactorFunction(10));
        filterFunctionBuilders[1] = new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("description", keyword),
                ScoreFunctionBuilders.weightFactorFunction(5));
        filterFunctionBuilders[2] = new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("keywords", keyword),
                ScoreFunctionBuilders.weightFactorFunction(2));
        filterFunctionBuilders[3] = new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("brandId", brandId),
                ScoreFunctionBuilders.weightFactorFunction(3));
        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(filterFunctionBuilders)
                .scoreMode(FunctionScoreQuery.ScoreMode.SUM)
                .setMinScore(2);
        nativeSearchQueryBuilder.withQuery(functionScoreQueryBuilder);
        // 过滤相同商品
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.mustNot(QueryBuilders.termQuery("id", id));
        nativeSearchQueryBuilder.withFilter(boolQueryBuilder);
        NativeSearchQuery searchQuery = nativeSearchQueryBuilder.build();
        SearchHits<EsProduct> searchHits = elasticsearchRestTemplate.search(searchQuery, EsProduct.class);
        if (searchHits.getTotalHits() <= 0) {
            return new PageImpl<>(ListUtil.empty(), pageable, 0);
        }
        List<EsProduct> products = searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
        return new PageImpl<>(products, pageable, searchHits.getTotalHits());
    }
}
