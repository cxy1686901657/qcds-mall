package com.qc.mall.order.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qc.mall.bean.OmsOrder;
import com.qc.mall.bean.OmsOrderItem;
import com.qc.mall.consts.RedisConst;
import com.qc.mall.order.mapper.OmsOrderItemMapper;
import com.qc.mall.order.mapper.OmsOrderMapper;
import com.qc.mall.service.OrderService;
import com.qc.mall.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @author qc
 * @date 2019/9/28
 * @description
 * @project mall
 */
@Service
@Component
public class OrderServiceImpl implements OrderService {
    @Autowired
    RedisUtil redisUtil;

    @Autowired
    OmsOrderMapper omsOrderMapper;

    @Autowired
    OmsOrderItemMapper omsOrderItemMapper;

    @Override
    public String checkTradeCode(String memberId, String tradeCode) {
        Jedis jedis = null ;

        try {
            jedis = redisUtil.getJedis();
            String tradeKey = RedisConst.TRADECODE.prefix + memberId + RedisConst.TRADECODE.sufix;
//            String tradeCodeFromCache = jedis.get(tradeKey);// 使用lua脚本在发现key的同时将key删除，防止并发订单攻击
            //对比防重删令牌
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            Long eval = (Long) jedis.eval(script, Collections.singletonList(tradeKey), Collections.singletonList(tradeCode));
            if (eval!=null&&eval!=0) {
                // jedis.del(tradeKey);
                return "success";
            } else {
                return "fail";
            }
        }finally {
            jedis.close();
        }

    }

    @Override
    public String genTradeCode(String memberId) {
        Jedis jedis = redisUtil.getJedis();

        String tradeKey = RedisConst.TRADECODE.prefix + memberId + RedisConst.TRADECODE.sufix;

        String tradeCode = UUID.randomUUID().toString();

        jedis.setex(tradeKey,60*15,tradeCode);

        jedis.close();

        return tradeCode;
    }

    @Override
    public void saveOrder(OmsOrder omsOrder) {
        // 保存订单表
        List<OmsOrderItem> omsOrderItems = omsOrder.getOmsOrderItems();
        omsOrderMapper.insertSelective(omsOrder);
        omsOrder= omsOrderMapper.selectByOrderSn(omsOrder.getOrderSn());
        String orderId = omsOrder.getId();
        // 保存订单详情
        for (OmsOrderItem omsOrderItem : omsOrderItems) {
            omsOrderItem.setOrderId(orderId);
            omsOrderItemMapper.insertSelective(omsOrderItem);
            // 删除购物车数据
            // cartService.delCart();
        }
    }

    @Override
    public OmsOrder getOrderByOutTradeNo(String outTradeNo) {
        OmsOrder omsOrder = omsOrderMapper.selectByOrderSn(outTradeNo);
        return omsOrder;
    }

    @Override
    public void updateOrder(OmsOrder omsOrder) {
        omsOrder.setStatus(1);
        omsOrderMapper.updatePayStatusByOrderSn(omsOrder);
    }
}
