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
import org.springframework.security.access.prepost.PreAuthorize;
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
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CommonResult<PmsBrand> addBrand(@RequestBody PmsBrand brand) {
        int count = brandService.addBrand(brand);
        if (count > 0) {
            log.info("insert brand successfully: {}", brand);
            return CommonResult.success(brand);
        }
        else{
            log.info("insert brand failed");
            return CommonResult.failed("fail");
        }
    }

    @ApiOperation("更新品牌信息")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CommonResult<PmsBrand> updateBrand(@PathVariable Long id, @RequestBody PmsBrand brand) {
        int count = brandService.updateBrand(id, brand);
        if (count > 0) {
            log.info("update brand successfully: {}", brand);
            return CommonResult.success(brand);
        }
        else{
            log.info("update brand failed");
            return CommonResult.failed("failed");
        }
    }

    @ApiOperation("删除品牌")
    @RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CommonResult<PmsBrand> removeBrandById(@PathVariable("id") Long id) {
        int count = brandService.removeBrandById(id);
        if (count > 0) {
            log.info("delete brand successfully: {}", id);
            return CommonResult.success(null);
        }
        else{
            log.info("delete brand failed");
            return CommonResult.failed("failed");
        }
    }

    @ApiOperation("查询品牌信息")
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public CommonResult<PmsBrand> getBrandById(@PathVariable("id") Long id) {
        PmsBrand brand = brandService.getBrandById(id);
        if (brand != null) {
            log.info("select brand successfully: {}", brand);
            return CommonResult.success(brand);
        }
        else {
            log.info("select brand failed");
            return CommonResult.failed("failed");
        }
    }


}
