package com.example.springbootmall.service.impl;

import com.example.springbootmall.dao.PmsBrandDao;
import com.example.springbootmall.model.PmsBrand;
import com.example.springbootmall.service.PmsBrandService;
import com.example.springbootmall.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PmsBrandServiceImpl implements PmsBrandService {

    private static final Logger log = LoggerFactory.getLogger(PmsBrandServiceImpl.class);
    @Autowired
    private RedisService redisService;
    @Value("${redis.key.prefix.brand}")
    private String REDIS_KEY_PREFIX_BRAND;
    @Value("${redis.key.expire.brand}")
    private Long REDIS_KEY_EXPIRE_BRAND;

    @Autowired
    private PmsBrandDao pmsBrandDao;

    private void freeRedis() {
        List<Object> brandIds = redisService.lrange(REDIS_KEY_PREFIX_BRAND + "allBrandIds", 0, -1);
        for (Object brandId : brandIds) {
            redisService.del(REDIS_KEY_PREFIX_BRAND + "brandId:" + brandId);
        }
        redisService.del(REDIS_KEY_PREFIX_BRAND + "allBrandIds");
    }

    @Override
    public int addBrand(PmsBrand brand) {
        // if brand already exists
        List<PmsBrand> brands = getAllBrand();
        for (PmsBrand oldBrand : brands) {
            if (oldBrand.getName().equals(brand.getName())) {
                log.info("brand already exists");
                return 0;
            }
        }

        // Cache Aside Pattern
        int result = pmsBrandDao.insert(brand);
        if (result == 0) {
            log.info("add brand failed");
            return 0;
        }
        freeRedis();

        return result;
    }

    @Override
    public int updateBrand(Long brandId, PmsBrand brand) {
        // Cache Aside Pattern
        brand.setId(brandId);
        int result = pmsBrandDao.update(brand);
        if (result == 0) {
            log.info("update brand failed");
            return 0;
        }
        freeRedis();

        return result;
    }

    @Override
    public int removeBrandById(Long brandId) {
        // Cache Aside Pattern
        int result = pmsBrandDao.deleteByPrimaryKey(brandId);
        if (result == 0) {
            log.info("remove brand failed");
            return 0;
        }
        freeRedis();

        return result;
    }

    @Override
    public PmsBrand getBrandById(Long brandId) {
        // search in redis first
        PmsBrand brand = (PmsBrand) redisService.get(REDIS_KEY_PREFIX_BRAND + "brandId:" + brandId);
        if (brand != null) {
            return brand;
        }

        // search in mysql
        brand = pmsBrandDao.selectByPrimaryKey(brandId);

        // store in redis
        if (brand != null) {
            redisService.set(REDIS_KEY_PREFIX_BRAND + "brandId:" + brandId, brand);
            redisService.expire(REDIS_KEY_PREFIX_BRAND + "brandId:" + brandId, REDIS_KEY_EXPIRE_BRAND);
            return brand;
        }
        return brand;
    }

    @Override
    public List<PmsBrand> getAllBrand() {
        // search in redis first
        List<Object> brandIds = redisService.lrange(REDIS_KEY_PREFIX_BRAND + "allBrandIds", 0, -1);
        List<PmsBrand> brands = new ArrayList<>();
        if(brandIds != null && !brandIds.isEmpty()) {
            for (Object brandId : brandIds) {
                PmsBrand brand = getBrandById((Long) brandId);
                brands.add(brand);
            }
            return brands;
        }

        // search in mysql
        brands = pmsBrandDao.selectAll();

        // store in redis
        if (brands != null && !brands.isEmpty()) {
            for (PmsBrand brand : brands) {
                redisService.lpush(REDIS_KEY_PREFIX_BRAND + "allBrandIds", brand.getId());
            }
        }
        return brands;
    }
}
