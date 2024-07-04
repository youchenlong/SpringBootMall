package com.example.springbootmall.service;

import java.util.List;
import java.util.Map;

public interface RedisService {
    void set(String key, Object value);
    Object get(String key);
    void hSet(String key, String field, Object value);
    Object hGet(String key, String field);
    void hSetAll(String key, Map<String, Object> map);
    Map<Object, Object> hGetAll(String key);
    void lpush(String key, Object value);
    List<Object> lrange(String key, int start, int end);
    Boolean del(String key);
    Boolean expire(String key, long time);
    Long getExpire(String key);
}
