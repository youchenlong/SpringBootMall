package com.example.springbootmall.service.impl;

import com.example.springbootmall.dao.PmsBrandDao;
import com.example.springbootmall.model.PmsBrand;
import com.example.springbootmall.service.PmsBrandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PmsBrandServiceImpl implements PmsBrandService {

    private static final Logger log = LoggerFactory.getLogger(PmsBrandServiceImpl.class);

    @Autowired
    private PmsBrandDao pmsBrandDao;

    @Override
    @Transactional
    public int addBrand(PmsBrand brand) {
        if (brand == null) {
            return 0;
        }
        // if brand already exists
        List<PmsBrand> brands = getAllBrand();
        if (brands != null && !brands.isEmpty()) {
            for (PmsBrand oldBrand : brands) {
                if (oldBrand.getName().equals(brand.getName())) {
                    log.info("brand already exists");
                    return 0;
                }
            }
        }

        return pmsBrandDao.insert(brand);
    }

    @Override
    @Transactional
    public int updateBrand(Long brandId, PmsBrand brand) {
        if (brand == null) {
            return 0;
        }

        brand.setId(brandId);
        return pmsBrandDao.update(brand);
    }

    @Override
    @Transactional
    public int removeBrandById(Long brandId) {

        return pmsBrandDao.deleteByPrimaryKey(brandId);
    }

    @Override
    public PmsBrand getBrandById(Long brandId) {

        // search in mysql
        return pmsBrandDao.selectByPrimaryKey(brandId);
    }

    @Override
    public List<PmsBrand> getAllBrand() {

        // search in mysql
        return pmsBrandDao.selectAll();
    }
}
