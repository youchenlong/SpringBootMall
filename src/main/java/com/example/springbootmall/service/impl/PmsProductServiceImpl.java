package com.example.springbootmall.service.impl;

import com.example.springbootmall.dao.PmsProductDao;
import com.example.springbootmall.model.PmsProduct;
import com.example.springbootmall.service.PmsProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PmsProductServiceImpl implements PmsProductService {

    @Autowired
    private PmsProductDao pmsProductDao;

    @Override
    public int insertProduct(PmsProduct product) {
        return pmsProductDao.insert(product);
    }

    @Override
    public int updateProduct(Long id, PmsProduct product) {
        product.setId(id);
        return pmsProductDao.update(product);
    }

    @Override
    public int deleteProductById(Long id) {
        return pmsProductDao.deleteByPrimaryKey(id);
    }

    @Override
    public PmsProduct selectProductById(Long id) {
        return pmsProductDao.selectByPrimaryKey(id);
    }

    @Override
    public List<PmsProduct> selectAllProduct() {
        return pmsProductDao.selectAll();
    }
}
