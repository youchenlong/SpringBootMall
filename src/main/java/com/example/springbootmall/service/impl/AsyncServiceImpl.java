package com.example.springbootmall.service.impl;

import com.example.springbootmall.model.PmsBrand;
import com.example.springbootmall.service.AsyncService;
import com.example.springbootmall.service.PmsBrandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AsyncServiceImpl implements AsyncService {

    private static final Logger log = LoggerFactory.getLogger(AsyncServiceImpl.class);

    @Autowired
    PmsBrandService pmsBrandService;

    @Async("asyncServiceExecutor")
    @Override
    public void executeAsyncSelect() {
        log.info("executeAsyncSelect: {}", Thread.currentThread().getName());
        List<PmsBrand> allBrands =  pmsBrandService.selectAllBrand();
        if (allBrands != null && !allBrands.isEmpty()) {
            PmsBrand pmsBrand = allBrands.get(0);
            log.info(pmsBrand.toString());
        }
    }

    @Async("asyncServiceExecutor")
    @Override
    @Transactional
    public void executeAsyncInsert() {
        log.info("executeAsyncInsert: {}", Thread.currentThread().getName());
        PmsBrand pmsBrand = new PmsBrand();
        pmsBrand.setName("三星");
        pmsBrand.setFirstLetter("S");
        pmsBrand.setFactoryStatus(1);
        pmsBrand.setBrandStory("三星集团（英文：SAMSUNG、韩文：삼성）是韩国最大的跨国企业集团，三星集团包括众多的国际下属企业，旗下子公司有：三星电子、三星物产、三星人寿保险等，业务涉及电子、金融、机械、化学等众多领域。");
        pmsBrandService.insertBrand(pmsBrand);
    }

    @Async("asyncServiceExecutor")
    @Override
    @Transactional
    public void executeAsyncDelete() {
        log.info("executeAsyncDelete: {}", Thread.currentThread().getName());
        List<PmsBrand> allBrands = pmsBrandService.selectAllBrand();
        if (allBrands != null && !allBrands.isEmpty()) {
            PmsBrand pmsBrand = allBrands.get(0);
            pmsBrandService.deleteBrandById(pmsBrand.getId());
        }
        else{
            log.info("executeAsyncDelete: {} failed", Thread.currentThread().getName());
        }
    }

    @Override
    public void executeSelect(){
        log.info("executeSelect: {}", Thread.currentThread().getName());
        List<PmsBrand> allBrands =  pmsBrandService.selectAllBrand();
        if (allBrands != null && !allBrands.isEmpty()) {
            PmsBrand pmsBrand = allBrands.get(0);
            log.info(pmsBrand.toString());
        }
    }

    @Override
    @Transactional
    public void executeInsert(){
        log.info("executeInsert: {}", Thread.currentThread().getName());
        PmsBrand pmsBrand = new PmsBrand();
        pmsBrand.setName("华为");
        pmsBrand.setFirstLetter("H");
        pmsBrand.setFactoryStatus(1);
        pmsBrand.setBrandStory("荣耀品牌成立于2013年,是华为旗下手机双品牌之一。荣耀以“创新、品质、服务”为核心战略,为全球年轻人提供潮酷的全场景智能化体验,打造年轻人向往的先锋文化和潮流生活方式");
        pmsBrandService.insertBrand(pmsBrand);
    }

    @Override
    @Transactional
    public void executeDelete(){
        log.info("executeDelete: {}", Thread.currentThread().getName());
        List<PmsBrand> allBrands = pmsBrandService.selectAllBrand();
        if (allBrands != null && !allBrands.isEmpty()) {
            PmsBrand pmsBrand = allBrands.get(0);
            pmsBrandService.deleteBrandById(pmsBrand.getId());
        }
        else{
            log.info("executeDelete: {} failed", Thread.currentThread().getName());
        }
    }
}
