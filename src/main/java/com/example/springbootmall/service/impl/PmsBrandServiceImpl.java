package com.example.springbootmall.service.impl;

import com.example.springbootmall.dao.PmsBrandDao;
import com.example.springbootmall.model.PmsBrand;
import com.example.springbootmall.service.PmsBrandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PmsBrandServiceImpl implements PmsBrandService {

    private static final Logger log = LoggerFactory.getLogger(PmsBrandServiceImpl.class);

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
        log.info("selectBrandById id:{}", id);
        return pmsBrandDao.selectByPrimaryKey(id);
    }

    @Override
    public List<PmsBrand> selectAllBrand() {
        log.info("selectAllBrand");
        return pmsBrandDao.selectAll();
    }
}
