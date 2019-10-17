package com.qc.mall.cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.qc.mall")
public class MallCartWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallCartWebApplication.class, args);
    }

}
