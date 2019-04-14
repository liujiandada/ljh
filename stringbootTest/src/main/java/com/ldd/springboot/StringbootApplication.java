package com.ldd.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching//开启redis缓存
public class StringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(StringbootApplication.class, args);
    }

}
