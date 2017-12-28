package com.luck.cloud.gateway.spring.mybatis;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import java.util.Properties;


/**
 * mybatis 通用Mapper核心类，勿动
 * Created by luck on 2017/09/28.
 */
@Configuration
@AutoConfigureAfter(MyBatisConfig.class)
public class MyBatisMapperScannerConfig {
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("com.luck.cloud.gateway.dao");

        //初始化扫描器的相关配置，这里我们要创建一个Mapper的父类
        Properties properties = new Properties();
        properties.setProperty("mappers", "com.luck.cloud.MyMapper");
        properties.setProperty("notEmpty", "false");
        properties.setProperty("IDENTITY", "MYSQL");

        mapperScannerConfigurer.setProperties(properties);

        return mapperScannerConfigurer;
    }
}
