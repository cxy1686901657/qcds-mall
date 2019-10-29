package com.qc.mall.cart.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.qc.mall.bean.OmsCartItem;
import com.qc.mall.cart.mapper.OmsCartItemMapper;
import com.qc.mall.consts.MqQueueConst;
import com.qc.mall.consts.RedisConst;
import com.qc.mall.service.CartService;
import com.qc.mall.util.ActiveMQUtil;
import com.qc.mall.util.RedisUtil;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;

/**
 * @author qc
 * @date 2019/9/17
 * @description
 * @project mall
 */
@Service
@Component
@Slf4j
public class CartServiceImpl implements CartService {
    @Autowired
    OmsCartItemMapper omsCartItemMapper;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    ActiveMQUtil activeMQUtil;
    @Override
    public OmsCartItem ifCartExistByUser(String memberId, String skuId) {
        OmsCartItem omsCartItem = omsCartItemMapper.selectOneByMemberIdAndSkuId(memberId,skuId);
        return omsCartItem;
    }
    @Override
    public void addCart(OmsCartItem omsCartItem) {
        if (StringUtils.isNotBlank(omsCartItem.getMemberId())) {
            omsCartItemMapper.insertSelective(omsCartItem);//避免添加空值
        }
    }

    @Override
    public void updateCart(OmsCartItem omsCartItemFromDb) {
        omsCartItemMapper.updateByPrimaryKeySelective(omsCartItemFromDb);
    }

    @Override
    public void flushCartCache(String memberId) {
        List<OmsCartItem> omsCartItems = omsCartItemMapper.selectAllByMemberId(memberId);
        // 同步到redis缓存中
        @Cleanup Jedis jedis = redisUtil.getJedis();
        jedis.set(RedisConst.CART.prefix+memberId+RedisConst.CART.sufix,JSON.toJSONString(omsCartItems));
    }

    @Override
    public List<OmsCartItem> cartList(String memberId) {
        Jedis jedis=redisUtil.getJedis();
        try{
            String s = jedis.get(RedisConst.CART.prefix + memberId + RedisConst.CART.sufix);
            if(StringUtils.isBlank(s)||s.equals("")){
                List<OmsCartItem> omsCartItems = omsCartItemMapper.selectAllByMemberId(memberId);
                jedis.set(RedisConst.CART.prefix + memberId +
                        RedisConst.CART.sufix, JSON.toJSONString(omsCartItems));
                return omsCartItems;
            }else{
                return JSON.parseArray(s, OmsCartItem.class);
            }
        }catch (Exception e){
            log.error("{}",e);
        }finally {
            jedis.close();
        }
        return null;
    }

    @Override
    public void checkCart(OmsCartItem omsCartItem) {
//        Jedis jedis=redisUtil.getJedis();
//        try{
//            String s = jedis.get(RedisConst.CART.prefix + omsCartItem.getMemberId() + RedisConst.CART.sufix);
//            List<OmsCartItem> omsCartItems = JSON.parseArray(s, OmsCartItem.class);
//            omsCartItems.stream().forEach(x->{
//                if(x.getProductSkuId().equals(omsCartItem.getProductSkuId())){
//                    x.setIsChecked(omsCartItem.getIsChecked());
//                }
//            });
//            jedis.set(RedisConst.CART.prefix + omsCartItem.getMemberId() +
//                    RedisConst.CART.sufix, JSON.toJSONString(omsCartItems));
//        }catch (Exception e){
//            log.error("{}",e);
//        }finally {
//            jedis.close();
//        }
        omsCartItemMapper.updateByMemberIdAndSkuId(omsCartItem.getMemberId(),omsCartItem.getProductSkuId(),omsCartItem.getIsChecked());
        // 缓存同步
        flushCartCache(omsCartItem.getMemberId());
    }

    @Override
    public void syncflushCartCache(String memberId) {
        HashMap<String, String> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("memberId", memberId);
        activeMQUtil.sendTransactedMapMessage(MqQueueConst.UPDATE_CARTCACHE,objectObjectHashMap );
    }
}
