package com.example.springbootmall.dao;

import com.example.springbootmall.model.PmsBrand;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PmsBrandDaoTest {

    private static final Logger log = LoggerFactory.getLogger(PmsBrandDaoTest.class);

    @Autowired
    private PmsBrandDao pmsBrandDao;

    @Test
    void insert() {
        PmsBrand pmsBrand = new PmsBrand();
        pmsBrand.setName("三星");
        pmsBrand.setFirstLetter("S");
        pmsBrand.setSort(100);
        pmsBrand.setFactoryStatus(1);
        pmsBrand.setShowStatus(1);
        pmsBrand.setProductCount(100);
        pmsBrand.setProductCommentCount(100);
        pmsBrand.setLogo("http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20200607/57201b47N7bf15715.jpg");
        pmsBrand.setBigPic("http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20221108/sanxing_banner_01.png");
        pmsBrand.setBrandStory("三星集团（英文：SAMSUNG、韩文：삼성）是韩国最大的跨国企业集团，三星集团包括众多的国际下属企业，旗下子公司有：三星电子、三星物产、三星人寿保险等，业务涉及电子、金融、机械、化学等众多领域。");
        int result = pmsBrandDao.insert(pmsBrand);
        log.info("insert id={}, result:{}", pmsBrand.getId(), result);
    }

    @Test
    void updateByPrimaryKey() {
        PmsBrand pmsBrand = new PmsBrand();
        pmsBrand.setId(23L);
        pmsBrand.setName("三星");
        pmsBrand.setFirstLetter("S");
        pmsBrand.setSort(100);
        pmsBrand.setFactoryStatus(1);
        pmsBrand.setShowStatus(1);
        pmsBrand.setProductCount(50);
        pmsBrand.setProductCommentCount(100);
        pmsBrand.setLogo("http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20200607/57201b47N7bf15715.jpg");
        pmsBrand.setBigPic("http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20221108/sanxing_banner_01.png");
        pmsBrand.setBrandStory("三星集团（英文：SAMSUNG、韩文：삼성）是韩国最大的跨国企业集团，三星集团包括众多的国际下属企业，旗下子公司有：三星电子、三星物产、三星人寿保险等，业务涉及电子、金融、机械、化学等众多领域。");
        int result = pmsBrandDao.update(pmsBrand);
        log.info("update id={}, result:{}", pmsBrand.getId(), result);
    }

    @Test
    void selectByPrimaryKey() {
        PmsBrand pmsBrand = pmsBrandDao.selectByPrimaryKey(23L);
        log.info("select id={}, result:{}", pmsBrand.getId(), pmsBrand);
    }

    @Test
    void deleteByPrimaryKey() {
        int result = pmsBrandDao.deleteByPrimaryKey(23L);
        log.info("delete id={}, result:{}", 23L, result);
    }
}