package com.example.springbootmall.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class test {

    @RequestMapping(value="/index", method= RequestMethod.GET)
    public String index(){
        return "Welcome to Spring Boot Mall";
    }
}
