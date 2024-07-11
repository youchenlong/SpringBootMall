package com.example.springbootmall.service;

import com.example.springbootmall.model.PmsBrand;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PmsBrandServiceTest {

    private static final Logger log = LoggerFactory.getLogger(PmsBrandServiceTest.class);
    @Autowired
    private PmsBrandService pmsBrandService;

    @Test
    void addBrand() {
        PmsBrand pmsBrand = new PmsBrand();
        pmsBrand.setName("万和");
        pmsBrand.setFirstLetter("W");
        pmsBrand.setFactoryStatus(1);
        pmsBrand.setBrandStory("万和成立于1993年8月，总部位于广东顺德国家级高新技术开发区内，是国内生产规模最大的燃气具专业制造企业，也是中国燃气具发展战略的首倡者和推动者、中国五金制品协会燃气用具分会第三届理事长单位。");
        int result = pmsBrandService.addBrand(pmsBrand);
        log.info("insert id={}, result:{}", pmsBrand.getId(), result);
    }

    @Test
    void updateBrand() {
        PmsBrand pmsBrand = pmsBrandService.getBrandById(1L);
        // do nothing
        int result = pmsBrandService.updateBrand(1L, pmsBrand);
        log.info("update id={}, result:{}", pmsBrand.getId(), result);
    }

    @Test
    void removeBrandById() {
        int result = pmsBrandService.removeBrandById(1L);
        log.info("remove id={}, result:{}", 1L, result);
    }

    @Test
    void getBrandById() {
        PmsBrand pmsBrand = pmsBrandService.getBrandById(1L);
        log.info("get id={}, result:{}", 1L, pmsBrand);
    }

    @Test
    void getAllBrand() {
        List<PmsBrand> pmsBrands = pmsBrandService.getAllBrand();
        for (PmsBrand brand : pmsBrands) {
            log.info("get id={}, brand:{}", brand.getId(), brand);
        }
    }
}