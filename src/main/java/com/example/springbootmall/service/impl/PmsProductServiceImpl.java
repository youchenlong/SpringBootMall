package com.example.springbootmall.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import com.example.springbootmall.dao.PmsProductDao;
import com.example.springbootmall.model.PmsProduct;
import com.example.springbootmall.service.PmsProductService;
import com.example.springbootmall.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class PmsProductServiceImpl implements PmsProductService {

    private static final Logger log = LoggerFactory.getLogger(PmsProductServiceImpl.class);

    @Autowired
    private PmsProductDao pmsProductDao;

    @Autowired
    private RedisService redisService;
    @Value("${redis.key.prefix.product}")
    private String REDIS_KEY_PREFIX_PRODUCT;
    @Value("${redis.key.expire.product}")
    private Long REDIS_KEY_EXPIRE_PRODUCT;

    private void freeRedis() {
        redisService.delAllByPrefix(REDIS_KEY_PREFIX_PRODUCT);
    }

    @Override
//    @Transactional
    public int addProduct(PmsProduct product) {
        if (product == null) {
            return 0;
        }
        // if product already exists
        List<PmsProduct> products = getAllProduct();
        for (PmsProduct oldProduct : products) {
            if (oldProduct.getName().equals(product.getName()) && oldProduct.getBrandId().equals(product.getBrandId())) {
                log.info("product already exists");
                return 0;
            }
        }

        // Cache Aside Pattern
        int result = pmsProductDao.insert(product);
        if (result == 0) {
            log.info("add product failed");
            return 0;
        }
        freeRedis();

        return result;
    }

    @Override
//    @Transactional
    public int updateProduct(Long productId, PmsProduct product) {
        if (product == null) {
            return 0;
        }
        // Cache Aside Pattern
        product.setId(productId);
        int result = pmsProductDao.update(product);
        if (result == 0) {
            log.info("update product failed");
            return 0;
        }
        freeRedis();

        return result;
    }

    @Override
//    @Transactional
    public int removeProductById(Long productId) {
        // Cache Aside Pattern
        int result = pmsProductDao.deleteByPrimaryKey(productId);
        if (result == 0) {
            log.info("remove product failed");
            return 0;
        }
        freeRedis();

        return result;
    }

    @Override
    public PmsProduct getProductById(Long productId) {
        // search in redis first
        Map<Object, Object> map = redisService.hGetAll(REDIS_KEY_PREFIX_PRODUCT + "productId:" + productId);
        PmsProduct product = BeanUtil.fillBeanWithMap(map, new PmsProduct(), false);
        if (!map.isEmpty()) {
            return product;
        }

        // search in mysql
        product = pmsProductDao.selectByPrimaryKey(productId);

        // store in redis
        if (product != null) {
            redisService.hSetAll(REDIS_KEY_PREFIX_PRODUCT + "productId:" + productId, BeanUtil.beanToMap(product));
            redisService.expire(REDIS_KEY_PREFIX_PRODUCT + "productId:" + productId, REDIS_KEY_EXPIRE_PRODUCT);
            return product;
        }
        return null;
    }

    @Override
    public List<PmsProduct> getAllProduct() {
        // search in redis first
        List<Object> productIds = redisService.lrange(REDIS_KEY_PREFIX_PRODUCT + "allProductIds", 0, -1);
        List<PmsProduct> products = new ArrayList<>();
        if (productIds != null && !productIds.isEmpty()) {
            for (Object productId : productIds) {
                PmsProduct product = getProductById(Convert.convert(Long.class, productId));
                products.add(product);
            }
            return products;
        }

        // search in mysql
        products = pmsProductDao.selectAll();

        // store in redis
        if (products != null && !products.isEmpty()) {
            for (PmsProduct product : products) {
                redisService.lpush(REDIS_KEY_PREFIX_PRODUCT + "allProductIds", product.getId());
            }
            return products;
        }
        return null;
    }

    @Override
    public List<PmsProduct> getAllProductBySale() {
        // search in redis first
        Set<Object> rankProducts = redisService.zRevRange(REDIS_KEY_PREFIX_PRODUCT + "sales", 0, -1);
        List<PmsProduct> products = new ArrayList<>();
        if (rankProducts != null && !rankProducts.isEmpty()) {
            for (Object product : rankProducts) {
                products.add((PmsProduct) product);
            }
            return products;
        }

        // search in mysql
        products = pmsProductDao.selectAllBySale();

        // store in redis
        if (products != null && !products.isEmpty()) {
            for (PmsProduct product : products) {
                redisService.zAdd(REDIS_KEY_PREFIX_PRODUCT + "sales", product.getId(), product.getSale());
            }
            return products;
        }
        return null;
    }
}
