package com.example.springbootmall.service;

import com.example.springbootmall.model.PmsProduct;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PmsProductServiceTest {

    private static final Logger log = LoggerFactory.getLogger(PmsProductServiceTest.class);
    @Autowired
    private PmsProductService pmsProductService;

    @Test
    void addProduct() {
        PmsProduct pmsProduct = new PmsProduct();
        pmsProduct.setBrandId(1L);
        pmsProduct.setName("万和燃气热水器");
        pmsProduct.setDescription("天然气家用四重防冻直流变频节能全新升级增压水伺服恒温高抗风");
        pmsProduct.setKeywords("家用电器");
        pmsProduct.setPrice(BigDecimal.valueOf(7999));
        pmsProduct.setSale(0);
        pmsProduct.setStock(1000);
        int result = pmsProductService.addProduct(pmsProduct);
        log.info("addProduct id={}, result={}", pmsProduct.getId(), result);
    }

    @Test
    void updateProduct() {
        PmsProduct pmsProduct = pmsProductService.getProductById(3L);
        // do nothing
        pmsProduct.setSale(0);
        pmsProduct.setStock(1000);
        int result = pmsProductService.updateProduct(3L, pmsProduct);
        log.info("updateProduct id={}, result={}", pmsProduct.getId(), result);
    }

    @Test
    void removeProductById() {
        int result = pmsProductService.removeProductById(1L);
        log.info("removeProductById id={}, result={}", 1L, result);
    }

    @Test
    void getProductById() {
        PmsProduct pmsProduct = pmsProductService.getProductById(1L);
        log.info("getProductById id={}, result={}", pmsProduct.getId(), pmsProduct);
    }

    @Test
    void getAllProduct() {
        List<PmsProduct> pmsProducts = pmsProductService.getAllProduct();
        for (PmsProduct pmsProduct : pmsProducts) {
            log.info("getAllProduct id={}, result={}", pmsProduct.getId(), pmsProduct);
        }
    }

    @Test
    void getAllProductBySale() {
        List<PmsProduct> pmsProducts = pmsProductService.getAllProductBySale();
        for (PmsProduct pmsProduct : pmsProducts) {
            log.info("getAllProductBySale id={}, result={}", pmsProduct.getId(), pmsProduct);
        }
    }

    @Test
    void simpleSearch() {
        List<PmsProduct> pmsProducts = pmsProductService.simpleSearch("联想");
        for (PmsProduct pmsProduct : pmsProducts) {
            log.info("simpleSearch id={}, result={}", pmsProduct.getId(), pmsProduct);
        }
    }
}