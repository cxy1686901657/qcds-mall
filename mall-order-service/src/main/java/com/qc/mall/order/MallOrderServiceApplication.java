package com.qc.mall.order;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author qc
 * @date 2019/9/27
 * @description
 * @project mall
 */
@ComponentScan("com.qc.mall")
@EnableDubbo
@SpringBootApplication
@MapperScan("com.qc.mall.order.mapper")
public class MallOrderServiceApplication {
    public static void main(String[] args){
        SpringApplication.run(MallOrderServiceApplication.class, args);
    }
}
