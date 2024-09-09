package com.food.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = {"com.food.core.mapper"})
public class BackendCoreApplication {


    public static void main(String[] args) {
        SpringApplication.run(BackendCoreApplication.class, args);

    }


}
