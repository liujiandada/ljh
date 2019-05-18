package com.ldd.springboot.mybatisplus;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
     * @author liujian
     * @date 2019/05/18
     */
    @Configuration
    public class MyBatisPlusConfig {

        /**
         * mybatis-plus分页插件
         */
        @Bean
        public PaginationInterceptor paginationInterceptor() {
            PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
            return paginationInterceptor;
        }

        /**

         * 打印 sql

         */

        @Bean
        public PerformanceInterceptor performanceInterceptor() {


            PerformanceInterceptor performanceInterceptor =new PerformanceInterceptor();

            //格式化sql语句

//            Properties properties =new Properties();
//
//            properties.setProperty("format", "false");
//
//            performanceInterceptor.setProperties(properties);

            return performanceInterceptor;

        }

    }
