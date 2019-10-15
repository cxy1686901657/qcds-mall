package com.qc.mall.manage;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@MapperScan(basePackages = "com.qc.mall.manage.mapper")
@SpringBootApplication
@EnableDubbo
@ComponentScan("com.qc.mall")
public class MallManageServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallManageServiceApplication.class, args);
    }

}
