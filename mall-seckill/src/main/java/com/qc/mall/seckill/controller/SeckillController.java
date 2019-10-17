package com.qc.mall.seckill.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.qc.mall.Result;
import com.qc.mall.bean.PmsSkuInfo;
import com.qc.mall.service.SkuService;
import com.qc.mall.util.RedisUtil;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author qc
 * @date 2019/10/14
 * @description
 * @project mall
 */
@Controller
@RequestMapping("/seckill")
@Slf4j
public class SeckillController {

    @Autowired
    RedisUtil redisUtil;
    @Reference
    SkuService skuService;

    /**
     * seckill list
     * @return
     */
    @GetMapping("list")
    @ResponseBody
    public List seckillSkuList(){
        @Cleanup Jedis jedis=redisUtil.getJedis();
        String seckill = jedis.get("seckill");
        List<PmsSkuInfo> pmsSkuInfos = JSON.parseArray(seckill, PmsSkuInfo.class);
        return pmsSkuInfos;
    }

    /**
     * test add data to redis
     * @return
     */
    @GetMapping("test")
    @ResponseBody
    public List seckillSkuList1(){
        List<PmsSkuInfo> all = skuService.getAll();
        List<PmsSkuInfo> pmsSkuInfos = all.subList(0, 5);
        @Cleanup Jedis jedis=redisUtil.getJedis();
        jedis.setex("seckill", 7200, JSON.toJSONString(pmsSkuInfos));
        return pmsSkuInfos;
    }

    /**
     * random
     * @return
     */
    @RequestMapping("kill")
    @ResponseBody
    public Result randomkill(String skuid, HttpServletRequest httpServletRequest) {
        Result result=new Result();
        String memberId= (String) httpServletRequest.getAttribute("memberId");
        Jedis jedis = redisUtil.getJedis();
        int stock = Integer.parseInt(jedis.get(skuid));
        jedis.watch(skuid);
        if(stock>0){
            Transaction multi = jedis.multi();
            multi.incrBy(skuid, -1);
            List<Object> exec = multi.exec();
            if (exec!=null&&exec.size()>0){
                log.info("{}seckill success for {}*1",memberId,skuid);
                result.setMsg("success");
            }else{
                log.info("{}seckill fail",memberId);
                result.setMsg("fail");
            }
        }
        jedis.close();
        return result;
    }

//    /***
//     * 先到先得式秒杀
//     * @return
//     */
//    @RequestMapping("secKill")
//    @ResponseBody
//    public String secKill(){
//        Jedis jedis = redisUtil.getJedis();
//
//        RSemaphore semaphore = redissonClient.getSemaphore("106");
//        boolean b = semaphore.tryAcquire();
//
//        int stock = Integer.parseInt(jedis.get("106"));
//        if(b){
//            System.out.println("当前库存剩余数量"+stock+",某用户抢购成功，当前抢购人数："+(1000-stock));
//            // 用消息队列发出订单消息
//            System.out.println("发出订单的消息队列，由订单系统对当前抢购生成订单");
//        }else {
//            System.out.println("当前库存剩余数量"+stock+",某用户抢购失败");
//        }
//
//        jedis.close();
//        return "1";
//    }

}
