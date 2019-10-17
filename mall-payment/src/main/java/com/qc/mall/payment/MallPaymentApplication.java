package com.qc.mall.payment;


import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


/**
 * @author qc
 * @date 2019/9/30
 * @description
 * @project mall
 */
@ComponentScan("com.qc.mall")
@SpringBootApplication
@EnableDubbo
@MapperScan("com.qc.mall.payment.mapper")
public class MallPaymentApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallPaymentApplication.class, args);
    }
}
