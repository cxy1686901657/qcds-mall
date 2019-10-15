package com.qc.mall.manage;

import com.qc.mall.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MallManageServiceApplicationTests {
    @Autowired
    RedisUtil redisUtil;

    @Test
    public void contextLoads() {
        System.out.println(redisUtil.getJedis());
        System.out.println("1");
    }

}
