package com.ldd.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 打war包 需要 继承 SpringBootServletInitializer
 */

@SpringBootApplication
@EnableCaching//开启redis缓存
public class StringbootApplication/* extends SpringBootServletInitializer*/ {

//    /**
//     * 打war包 需要实现这个方法
//     * @param application
//     * @return
//     */
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(StringbootApplication.class);
//    }

    public static void main(String[] args) {
        SpringApplication.run(StringbootApplication.class, args);
    }

}
