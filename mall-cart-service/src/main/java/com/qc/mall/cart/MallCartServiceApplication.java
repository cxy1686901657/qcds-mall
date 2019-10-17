package com.qc.mall.cart;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.annotation.EnableJms;

@MapperScan(basePackages = "com.qc.mall.cart.mapper")
@SpringBootApplication
@EnableDubbo
@ComponentScan("com.qc.mall")
public class MallCartServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallCartServiceApplication.class, args);
    }

}
