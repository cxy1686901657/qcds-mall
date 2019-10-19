package com.qc.mall.item;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.qc.mall")
public class MallItemWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallItemWebApplication.class, args);
    }

}
