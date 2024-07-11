package com.example.springbootmall.service;

import com.sun.org.apache.xpath.internal.operations.Bool;

public interface BloomFilterService {
    void init();
    Boolean add(Object obj);
    Boolean contains(Object obj);
}
