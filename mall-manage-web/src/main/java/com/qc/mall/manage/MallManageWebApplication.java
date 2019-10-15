package com.qc.mall.manage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ComponentScan(value = "com.qc.mall")
public class MallManageWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallManageWebApplication.class, args);
    }

}
