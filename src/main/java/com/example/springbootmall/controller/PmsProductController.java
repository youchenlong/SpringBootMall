package com.example.springbootmall.controller;

import com.example.springbootmall.component.CommonResult;
import com.example.springbootmall.model.PmsProduct;
import com.example.springbootmall.service.PmsProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "PmsProductController")
@Tag(name = "PmsProductController", description = "商品搜索管理")
@RequestMapping("/product")
public class PmsProductController {
    @Autowired
    private PmsProductService productService;

    private static final Logger log = LoggerFactory.getLogger(PmsProductController.class);

    @ApiOperation("添加商品")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CommonResult<PmsProduct> addProduct(@RequestBody PmsProduct product) {
        int count = productService.addProduct(product);
        if (count > 0) {
//            log.info("insert product successfully: {}", product);
            return CommonResult.success(product);
        }
        else{
//            log.info("insert product failed");
            return CommonResult.failed("failed");
        }
    }

    @ApiOperation("更新商品信息")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public CommonResult<PmsProduct> updateProduct(@PathVariable("id") Long id, @RequestBody PmsProduct product) {
        int count = productService.updateProduct(id, product);
        if (count > 0) {
//            log.info("update product successfully: {}", product);
            return CommonResult.success(product);
        }
        else{
//            log.info("update product failed");
            return CommonResult.failed("failed");
        }
    }

    @ApiOperation("删除商品")
    @RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
    public CommonResult<PmsProduct> removeProductById(@PathVariable("id") Long id) {
        int count = productService.removeProductById(id);
        if (count > 0) {
//            log.info("delete product successfully: {}", id);
            return CommonResult.success(null);
        }
        else{
//            log.info("delete product failed");
            return CommonResult.failed("failed");
        }
    }

    @ApiOperation("查询商品信息")
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public CommonResult<PmsProduct> getProductById(@PathVariable("id") Long id) {
        PmsProduct product = productService.getProductById(id);
        if (product != null) {
//            log.info("select product successfully: {}", product);
            return CommonResult.success(product);
        }
        else{
//            log.info("select product failed");
            return CommonResult.failed("failed");
        }
    }

    @ApiOperation("查询所有商品信息")
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public CommonResult<List<PmsProduct>> getAllProduct() {
        List<PmsProduct> productList = productService.getAllProduct();
        if (productList != null && !productList.isEmpty()) {
//            log.info("select all product successfully: {}", productList);
            return CommonResult.success(productList);
        }
        else {
//            log.info("select all product failed");
            return CommonResult.failed("failed");
        }
    }
}
