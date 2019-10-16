package com.qc.mall.search;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDubbo
@ComponentScan("com.qc.mall")
public class MallSearchServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallSearchServiceApplication.class, args);
    }

}
