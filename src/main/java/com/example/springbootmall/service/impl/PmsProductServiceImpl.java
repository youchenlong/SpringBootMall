package com.example.springbootmall.service.impl;

import com.example.springbootmall.dao.PmsProductDao;
import com.example.springbootmall.model.PmsBrand;
import com.example.springbootmall.model.PmsProduct;
import com.example.springbootmall.service.PmsProductService;
import com.example.springbootmall.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PmsProductServiceImpl implements PmsProductService {

    private static final Logger log = LoggerFactory.getLogger(PmsProductServiceImpl.class);
    @Autowired
    private RedisService redisService;
    @Value("${redis.key.prefix.product}")
    private String REDIS_KEY_PREFIX_PRODUCT;
    @Value("${redis.key.expire.product}")
    private Long REDIS_KEY_EXPIRE_PRODUCT;

    @Autowired
    private PmsProductDao pmsProductDao;

    private void freeRedis() {
        List<Object> productIds = redisService.lrange(REDIS_KEY_PREFIX_PRODUCT + "allProductIds", 0, -1);
        for (Object productId : productIds) {
            redisService.del(REDIS_KEY_PREFIX_PRODUCT + "productId:" + productId);
        }
        redisService.del(REDIS_KEY_PREFIX_PRODUCT + "allProductIds");
    }

    @Override
    public int addProduct(PmsProduct product) {
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
    public int updateProduct(Long productId, PmsProduct product) {
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
        PmsProduct product = (PmsProduct) redisService.get(REDIS_KEY_PREFIX_PRODUCT + "productId:" + productId);
        if (product != null) {
            return product;
        }

        // search in mysql
        product = pmsProductDao.selectByPrimaryKey(productId);

        // store in redis
        if (product != null) {
            redisService.set(REDIS_KEY_PREFIX_PRODUCT + "productId:" + productId, product);
            redisService.expire(REDIS_KEY_PREFIX_PRODUCT + "productId:" + productId, REDIS_KEY_EXPIRE_PRODUCT);
            return product;
        }
        return product;
    }

    @Override
    public List<PmsProduct> getAllProduct() {
        // search in redis first
        List<Object> productIds = redisService.lrange(REDIS_KEY_PREFIX_PRODUCT + "allProductIds", 0, -1);
        List<PmsProduct> products = new ArrayList<>();
        if (productIds != null && !productIds.isEmpty()) {
            for (Object productId : productIds) {
                PmsProduct product = getProductById((Long) productId);
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
        }
        return products;
    }
}
