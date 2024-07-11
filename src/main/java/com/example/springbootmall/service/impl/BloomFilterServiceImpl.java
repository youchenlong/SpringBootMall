package com.example.springbootmall.service.impl;

import com.example.springbootmall.service.BloomFilterService;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class BloomFilterServiceImpl implements BloomFilterService {

    @Autowired
    private RedissonClient redissonClient;
    @Value("${bloom-filter.expected-insertions}")
    private String expectedInsertions;
    @Value("${bloom-filter.fpp}")
    private String fpp;

    private RBloomFilter<Object> bloomFilter;


    @Override
    @PostConstruct
    public void init() {
        bloomFilter = redissonClient.getBloomFilter("bloomFilter");
        bloomFilter.tryInit(Long.parseLong(expectedInsertions), Double.parseDouble(fpp));
    }

    @Override
    public Boolean add(Object obj) {
        return bloomFilter.add(obj);
    }

    @Override
    public Boolean contains(Object obj) {
        return bloomFilter.contains(obj);
    }
}
