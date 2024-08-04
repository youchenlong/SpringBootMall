package com.example.springbootmall.service.impl;

import com.example.springbootmall.dao.PmsProductDao;
import com.example.springbootmall.model.PmsProduct;
import com.example.springbootmall.service.PmsProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class PmsProductServiceImpl implements PmsProductService {

    private static final Logger log = LoggerFactory.getLogger(PmsProductServiceImpl.class);

    @Autowired
    private PmsProductDao pmsProductDao;

    @Override
    @Transactional
    public int addProduct(PmsProduct product) {
        if (product == null) {
            return 0;
        }
        // if product already exists
        List<PmsProduct> products = getAllProduct();
        if (products != null && !products.isEmpty()) {
            for (PmsProduct oldProduct : products) {
                if (oldProduct.getName().equals(product.getName()) && oldProduct.getBrandId().equals(product.getBrandId())) {
                    log.info("product already exists");
                    return 0;
                }
            }
        }

        return pmsProductDao.insert(product);
    }

    @Override
    @Transactional
    public int updateProduct(Long productId, PmsProduct product) {
        if (product == null) {
            return 0;
        }

        product.setId(productId);
        return pmsProductDao.update(product);
    }

    @Override
//    @Transactional
    public int removeProductById(Long productId) {

        return pmsProductDao.deleteByPrimaryKey(productId);
    }

    @Override
    public PmsProduct getProductById(Long productId) {

        // search in mysql
        return pmsProductDao.selectByPrimaryKey(productId);
    }

    @Override
    public List<PmsProduct> getAllProduct() {

        // search in mysql
        return pmsProductDao.selectAll();
    }

    @Override
    public List<PmsProduct> getAllProductBySale() {

        // search in mysql
        return pmsProductDao.selectAllBySale();
    }
}
