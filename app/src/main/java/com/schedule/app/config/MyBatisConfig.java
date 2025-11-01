package com.schedule.app.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.schedule.app.repository")
public class MyBatisConfig {
    
}