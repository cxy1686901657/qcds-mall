package com.qc.mall.passport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@ComponentScan("com.qc.mall")
public class MallPassportWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallPassportWebApplication.class, args);
    }

}
