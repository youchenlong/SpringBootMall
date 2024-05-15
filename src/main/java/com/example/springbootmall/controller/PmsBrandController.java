package com.example.springbootmall.controller;

import com.example.springbootmall.component.CommonResult;
import com.example.springbootmall.model.PmsBrand;
import com.example.springbootmall.service.PmsBrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "PmsBrandController")
@Tag(name = "PmsBrandController", description = "商品品牌管理")
@RequestMapping("/brand")
public class PmsBrandController {
    @Autowired
    private PmsBrandService brandService;

    private static final Logger log = LoggerFactory.getLogger(PmsBrandController.class);

    @ApiOperation("添加品牌")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public CommonResult createBrand(@RequestBody PmsBrand brand) {
        int count = brandService.createBrand(brand);
        if (count > 0) {
            log.info("create brand success: {}", brand);
            return CommonResult.success(brand);
        }
        else{
            log.info("create brand failed: {}", brand);
            return CommonResult.failed("failed");
        }
    }

    @ApiOperation("更新品牌信息")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public CommonResult updateBrand(@PathVariable Long id, @RequestBody PmsBrand brand) {
        int count = brandService.updateBrand(id, brand);
        if (count > 0) {
            log.info("update brand success: {}", brand);
            return CommonResult.success(brand);
        }
        else{
            log.info("update brand failed: {}", brand);
            return CommonResult.failed("failed");
        }
    }

    @ApiOperation("删除品牌")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public CommonResult deleteBrand(@PathVariable("id") Long id) {
        int count = brandService.deleteBrandById(id);
        if (count > 0) {
            log.info("delete brand success: {}", id);
            return CommonResult.success(null);
        }
        else{
            log.info("delete brand failed: {}", id);
            return CommonResult.failed("failed");
        }
    }

    @ApiOperation("查询品牌信息")
    @RequestMapping(value = "/select/{id}", method = RequestMethod.GET)
    public CommonResult selectBrandById(@PathVariable("id") Long id) {
        return CommonResult.success(brandService.selectBrandById(id));
    }


}
