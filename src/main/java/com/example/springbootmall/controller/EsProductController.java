package com.example.springbootmall.controller;

import com.example.springbootmall.component.CommonPage;
import com.example.springbootmall.component.CommonResult;
import com.example.springbootmall.domain.EsProduct;
import com.example.springbootmall.service.EsProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "EsProductController")
@Tag(name = "EsProductController", description = "搜索商品管理")
@RequestMapping("/esProduct")
public class EsProductController {
    @Autowired
    private EsProductService esProductService;

    @ApiOperation(value = "导入所有数据库中的商品到ES")
    @RequestMapping(value = "/importAll", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CommonResult<Integer> importAll() {
        int count = esProductService.importAll();
        return CommonResult.success(count);
    }

    @ApiOperation(value = "删除ES中所有商品")
    @RequestMapping(value = "/deleteAll", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CommonResult<Object> deleteAll() {
        esProductService.deleteAll();
        return CommonResult.success(null);
    }

    @ApiOperation(value = "根据id删除ES中的商品")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CommonResult<Object> delete(@PathVariable Long id) {
        esProductService.delete(id);
        return CommonResult.success(null);
    }

    @ApiOperation(value = "根据id创建商品")
    @RequestMapping(value = "/create/{id}", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CommonResult<EsProduct> create(@PathVariable Long id) {
        EsProduct esProduct = esProductService.create(id);
        if (esProduct != null) {
            return CommonResult.success(esProduct);
        }
        else {
            return CommonResult.failed("failed");
        }
    }

    @ApiOperation(value = "简单搜索")
    @RequestMapping(value = "/sarch/simple", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public CommonResult<CommonPage<EsProduct>> search(@RequestParam(required = false) String keyword,
                                                      @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                      @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        Page<EsProduct> esProductPage = esProductService.search(keyword, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(esProductPage));
    }

    @ApiOperation(value = "复合搜索")
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public CommonResult<CommonPage<EsProduct>> search(@RequestParam(required = false) String keyword,
                                                      @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                      @RequestParam(required = false, defaultValue = "5") Integer pageSize,
                                                      @RequestParam(required = false, defaultValue = "1") Integer sort) {
        Page<EsProduct> esProductPage = esProductService.search(keyword, pageNum, pageSize, sort);
        return CommonResult.success(CommonPage.restPage(esProductPage));
    }

    @ApiOperation(value = "相关商品推荐")
    @RequestMapping(value = "/recommend/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public CommonResult<CommonPage<EsProduct>> recommend(@PathVariable Long id,
                                             @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                             @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        Page<EsProduct> esProductPage = esProductService.recommend(id, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(esProductPage));
    }
}
