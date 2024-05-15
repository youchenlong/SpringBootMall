package com.example.springbootmall.services.impl;

import com.example.springbootmall.dao.PmsBrandDao;
import com.example.springbootmall.model.PmsBrand;
import com.example.springbootmall.services.PmsBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PmsBrandServiceImpl implements PmsBrandService {

    @Autowired
    private PmsBrandDao pmsBrandDao;

    @Override
    public int createBrand(PmsBrand pmsBrand) {
        return pmsBrandDao.insert(pmsBrand);
    }

    @Override
    public int updateBrand(Long id, PmsBrand pmsBrand) {
        pmsBrand.setId(id);
        return pmsBrandDao.update(pmsBrand);
    }

    @Override
    public int deleteBrandById(Long id) {
        return pmsBrandDao.deleteByPrimaryKey(id);
    }

    @Override
    public PmsBrand selectBrandById(Long id) {
        return pmsBrandDao.selectByPrimaryKey(id);
    }
}
