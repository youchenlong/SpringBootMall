package com.example.springbootmall.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.example.springbootmall.dao")
public class MyBatisConfig {
}
