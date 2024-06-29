package com.example.springbootmall.dao;

import com.example.springbootmall.model.PmsBrand;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class PmsBrandDaoTest {

    private static final Logger log = LoggerFactory.getLogger(PmsBrandDaoTest.class);

    @Autowired
    private PmsBrandDao pmsBrandDao;

    @Test
    void insert() {
        PmsBrand pmsBrand = new PmsBrand();
        pmsBrand.setName("万和");
        pmsBrand.setFirstLetter("W");
        pmsBrand.setFactoryStatus(1);
        pmsBrand.setBrandStory("万和成立于1993年8月，总部位于广东顺德国家级高新技术开发区内，是国内生产规模最大的燃气具专业制造企业，也是中国燃气具发展战略的首倡者和推动者、中国五金制品协会燃气用具分会第三届理事长单位。");
        int result = pmsBrandDao.insert(pmsBrand);
        log.info("insert id={}, result:{}", pmsBrand.getId(), result);
    }

    @Test
    void updateByPrimaryKey() {
        PmsBrand pmsBrand = new PmsBrand();
        pmsBrand.setId(1L);
        pmsBrand.setName("万和");
        pmsBrand.setFirstLetter("W");
        pmsBrand.setFactoryStatus(1);
        pmsBrand.setBrandStory("万和成立于1993年8月，总部位于广东顺德国家级高新技术开发区内，是国内生产规模最大的燃气具专业制造企业，也是中国燃气具发展战略的首倡者和推动者、中国五金制品协会燃气用具分会第三届理事长单位。");
        int result = pmsBrandDao.update(pmsBrand);
        log.info("update id={}, result:{}", pmsBrand.getId(), result);
    }

    @Test
    void deleteByPrimaryKey() {
        int result = pmsBrandDao.deleteByPrimaryKey(1L);
        log.info("delete id={}, result:{}", 1L, result);
    }

    @Test
    void selectByPrimaryKey() {
        PmsBrand pmsBrand = pmsBrandDao.selectByPrimaryKey(1L);
        log.info("select id={}, result:{}", pmsBrand.getId(), pmsBrand);
    }

    @Test
    void selectAll() {
        List<PmsBrand> pmsBrands = pmsBrandDao.selectAll();
        for (PmsBrand pmsBrand : pmsBrands) {
            log.info("select id={}, result:{}", pmsBrand.getId(), pmsBrand);
        }
    }
}