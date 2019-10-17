package com.qc.mall.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author qc
 * @date 2019/9/26
 * @description
 * @project mall
 */
@ComponentScan("com.qc.mall")
@SpringBootApplication
public class MallOrderWebApplication {
    public static void main(String[] args){
        SpringApplication.run(MallOrderWebApplication.class, args);
    }
}
