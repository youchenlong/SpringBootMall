package com.example.springbootmall.controller;

import cn.hutool.core.util.RandomUtil;
import com.example.springbootmall.component.CommonResult;
import com.example.springbootmall.dao.PmsBrandDao;
import com.example.springbootmall.dao.PmsProductDao;
import com.example.springbootmall.model.PmsBrand;
import com.example.springbootmall.model.PmsProduct;
import com.example.springbootmall.model.UmsUser;
import com.example.springbootmall.service.UmsUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RestController
@Api(tags = "TestCaseController")
@Tag(name = "TestCaseController", description = "测试用例管理")
@RequestMapping("/testcase")
public class TestCaseController {

    private static final Logger log = LoggerFactory.getLogger(TestCaseController.class);

    @Autowired
    private UmsUserService umsUserService;
    @Autowired
    private PmsBrandDao pmsBrandDao;
    @Autowired
    private PmsProductDao pmsProductDao;

    @ApiOperation("批量生成用户用例")
    @RequestMapping(value = "generateUserTestCase", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public CommonResult<Object> generateUserTestCase() {
        String usernameStart = "202224021";
        String passwordStart = "202224021";
        String[] nicknames = {"Alice", "Bob", "Cindy", "David", "Frank", "George", "Helen", "Issac", "Joker", "Ken"};
        String telephoneStart = "13096190";
        String emailStart = "2091838";
        int[] genders = {0, 1, 2, 1, 1, 1, 0, 1, 0, 1};
        for (int i = 0; i < 10; i++) {
            UmsUser umsUser = new UmsUser();
            umsUser.setUsername(usernameStart + String.format("%03d", i));
            umsUser.setPassword(passwordStart + String.format("%03d", i));
            umsUser.setNickname(nicknames[i]);
            umsUser.setPhone(telephoneStart + String.format("%03d", i));
            umsUser.setEmail(emailStart + String.format("%03d", i) + "@qq.com");
            umsUser.setGender(genders[i]);
            umsUser.setCreateTime(new Date());
            umsUser.setBirthday(new Date());
            umsUserService.register(umsUser);
        }
        return CommonResult.success(null);
    }

    @ApiOperation("批量生成品牌用例")
    @RequestMapping(value = "/generateBrandTestCase", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public CommonResult<Object> generateBrandTestCase() {
        String[] brandNames = {"万和", "三星", "华为", "格力", "小米", "OPPO", "七匹狼", "海澜之家", "Apple", "NIKE",
                "联想", "美团", "京东", "腾讯", "百度", "得物", "顺丰", "vivo", "网易", "字节跳动"};
        String[] firstLetters = {"W", "S", "H", "G", "X", "O", "Q", "H", "A", "N",
                "L", "M", "J", "T", "B", "D", "S", "V", "W", "Z"};
        for (int i = 0; i < brandNames.length; i++) {
            PmsBrand pmsBrand = new PmsBrand();
            pmsBrand.setName(brandNames[i]);
            pmsBrand.setFirstLetter(firstLetters[i]);
            pmsBrand.setFactoryStatus(1);
            pmsBrand.setBrandStory(brandNames[i] + "品牌描述");
            pmsBrandDao.insert(pmsBrand);
        }
        return CommonResult.success(null);
    }

    @ApiOperation(value = "批量生成商品用例")
    @RequestMapping(value = "/generateProductTestCase", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public CommonResult<Object> generateProductTestCase() {
        List<PmsBrand> allBrands = pmsBrandDao.selectAll();
        String[] keywords = {"服装", "手机数码", "家用电器", "家具家装", "汽车用品"};
        for (PmsBrand allBrand : allBrands) {
            for (String keyword : keywords) {
                for (int version = 1; version <= 10000; version++) {
                    int price = RandomUtil.randomInt(100, 10000);
                    int sale = RandomUtil.randomInt(0, 1000);
                    int stock = 1000 - sale;
                    PmsProduct pmsProduct = new PmsProduct();
                    pmsProduct.setBrandId(allBrand.getId());
                    pmsProduct.setName(allBrand.getName() + keyword + "型号" + version);
                    pmsProduct.setDescription(allBrand.getName() + keyword + "型号" + version + "产品描述");
                    pmsProduct.setKeywords(keyword);
                    pmsProduct.setPrice(BigDecimal.valueOf(price));
                    pmsProduct.setSale(sale);
                    pmsProduct.setStock(stock);
                    pmsProductDao.insert(pmsProduct);
                }
            }
        }
        return CommonResult.success(null);
    }

}
