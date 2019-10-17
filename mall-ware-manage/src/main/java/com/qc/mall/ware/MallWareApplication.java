package com.qc.mall.ware;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author qc
 * @date 2019/10/17
 * @description
 * @project qcds-mall
 */
@SpringBootApplication
@MapperScan("com.qc.mall.ware.mapper")
@ComponentScan("com.qc.mall")
@EnableDubbo
public class MallWareApplication {
    public static void main(String[] args){
        SpringApplication.run(MallWareApplication.class, args);
    }
}
