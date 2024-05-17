package com.example.springbootmall.controller;

import com.example.springbootmall.service.AsyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class testController {

    private static final Logger log = LoggerFactory.getLogger(testController.class);

    @Autowired
    private AsyncService asyncService;

    @RequestMapping(value="/index", method= RequestMethod.GET)
    public String index(){
        return "Welcome to Spring Boot Mall";
    }

    @RequestMapping(value = "/testAsyncSelect", method = RequestMethod.GET)
    public void testAsyncSelect(){
        asyncService.executeAsyncSelect();
    }

    @RequestMapping(value = "/testAsyncInsert", method = RequestMethod.GET)
    public void testAsyncInsert(){
        asyncService.executeAsyncInsert();
    }

    @RequestMapping(value = "/testAsyncDelete", method = RequestMethod.GET)
    public void testAsyncDelete(){
        asyncService.executeAsyncDelete();
    }

    @RequestMapping(value = "/testSelect", method = RequestMethod.GET)
    public void testSelect(){
        asyncService.executeSelect();
    }

    @RequestMapping(value = "/testInsert", method = RequestMethod.GET)
    public void testInsert(){
        asyncService.executeInsert();
    }

    @RequestMapping(value = "/testDelete", method = RequestMethod.GET)
    public void testDelete(){
        asyncService.executeDelete();
    }
}
