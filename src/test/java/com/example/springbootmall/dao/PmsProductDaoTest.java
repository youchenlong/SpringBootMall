package com.example.springbootmall.dao;

import com.example.springbootmall.model.PmsProduct;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
class PmsProductDaoTest {

    private static final Logger log = LoggerFactory.getLogger(PmsProductDaoTest.class);

    @Autowired
    private PmsProductDao pmsProductDao;

    @Test
    void insert() {
        PmsProduct pmsProduct = new PmsProduct();
        pmsProduct.setBrandId(1L);
        pmsProduct.setName("万和燃气热水器");
        pmsProduct.setDescription("天然气家用四重防冻直流变频节能全新升级增压水伺服恒温高抗风");
        pmsProduct.setKeywords("家用电器");
        pmsProduct.setPrice(BigDecimal.valueOf(7999));
        pmsProduct.setSale(0);
        pmsProduct.setStock(1000);
        int result = pmsProductDao.insert(pmsProduct);
        log.info("insert id={}, result={}", pmsProduct.getId(), result);
    }

    @Test
    void update() {
        PmsProduct pmsProduct = new PmsProduct();
        pmsProduct.setId(1L);
        pmsProduct.setBrandId(1L);
        pmsProduct.setName("万和燃气热水器");
        pmsProduct.setDescription("天然气家用四重防冻直流变频节能全新升级增压水伺服恒温高抗风");
        pmsProduct.setKeywords("家用电器");
        pmsProduct.setPrice(BigDecimal.valueOf(7999));
        pmsProduct.setSale(0);
        pmsProduct.setStock(1000);
        int result = pmsProductDao.update(pmsProduct);
        log.info("update id={}, result={}", pmsProduct.getId(), result);
    }

    @Test
    void deleteByPrimaryKey() {
        int result = pmsProductDao.deleteByPrimaryKey(2L);
        log.info("delete id={}, result={}", 2L, result);
    }

    @Test
    void selectByPrimaryKey() {
        PmsProduct pmsProduct = pmsProductDao.selectByPrimaryKey(1L);
        log.info("select id={}, result={}", pmsProduct.getId(), pmsProduct);
    }

    @Test
    void selectAll() {
        List<PmsProduct> pmsProducts = pmsProductDao.selectAll();
        for (PmsProduct pmsProduct : pmsProducts) {
            log.info("select id={}, result={}", pmsProduct.getId(), pmsProduct);
        }
    }
}