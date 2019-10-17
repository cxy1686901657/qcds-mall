package com.qc.mall.seckill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


/**
 * @author qc
 * @date 2019/10/14
 * @description
 * @project mall
 */
@SpringBootApplication
@ComponentScan("com.qc.mall")
public class MallSeckillApplication {
    public static void main(String[] args){
        SpringApplication.run(MallSeckillApplication.class, args);
    }
}
