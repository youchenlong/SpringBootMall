package com.example.springbootmall.service;

public interface RedisService {
    void set(String key, Object value);
    Object get(String key);
    Boolean expire(String key, long time);
}
