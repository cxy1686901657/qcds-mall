package com.qc.mall.config;

import com.qc.mall.util.RedisUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author qc
 * @date 2019/8/29
 * @description
 * @project mall
 */
@Configuration
public class RedisConfig {
    @Value("${spring.myredis.host:disabled}")
    private String host;
    @Value("${spring.myredis.port}")
    private int port ;
    @Value("${spring.myredis.database}")
    private int database;
    @Bean
    public RedisUtil getRedisUtil(){
        if(host.equals("disabled")){
            return null;
        }
        RedisUtil redisUtil=new RedisUtil();
        redisUtil.initPool(host,port,database);
        return redisUtil;
    }
}
