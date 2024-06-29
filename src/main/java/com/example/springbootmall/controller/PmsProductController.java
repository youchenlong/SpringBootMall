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

@RestController
@Api(tags = "PmsProductController")
@Tag(name = "PmsProductController", description = "商品搜索管理")
@RequestMapping("/product")
public class PmsProductController {
    @Autowired
    private PmsProductService productService;

    private static final Logger log = LoggerFactory.getLogger(PmsProductController.class);

    @ApiOperation("添加商品")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public CommonResult insertProduct(@RequestBody PmsProduct product) {
        int count = productService.insertProduct(product);
        if (count > 0) {
            log.info("insert product success: {}", product);
            return CommonResult.success(product);
        }
        else{
            log.info("insert product fail: {}", product);
            return CommonResult.failed("fail");
        }
    }

    @ApiOperation("更新商品信息")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public CommonResult updateProduct(@PathVariable("id") Long id, @RequestBody PmsProduct product) {
        int count = productService.updateProduct(id, product);
        if (count > 0) {
            log.info("update product success: {}", product);
            return CommonResult.success(product);
        }
        else{
            log.info("update product fail: {}", product);
            return CommonResult.failed("fail");
        }
    }

    @ApiOperation("删除商品")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public CommonResult deleteProductById(@PathVariable("id") Long id) {
        int count = productService.deleteProductById(id);
        if (count > 0) {
            log.info("delete product success: {}", id);
            return CommonResult.success(id);
        }
        else{
            log.info("delete product fail: {}", id);
            return CommonResult.failed("fail");
        }
    }

    @ApiOperation("查询商品信息")
    @RequestMapping(value = "/select/{id}", method = RequestMethod.GET)
    public CommonResult selectProductById(@PathVariable("id") Long id) {
        return CommonResult.success(productService.selectProductById(id));
    }
}
