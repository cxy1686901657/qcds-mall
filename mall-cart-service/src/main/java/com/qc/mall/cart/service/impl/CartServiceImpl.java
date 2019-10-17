package com.qc.mall.cart.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.qc.mall.bean.OmsCartItem;
import com.qc.mall.cart.mapper.OmsCartItemMapper;
import com.qc.mall.service.CartService;
import com.qc.mall.util.RedisUtil;
import lombok.Cleanup;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author qc
 * @date 2019/9/17
 * @description
 * @project mall
 */
@Service
@Component
public class CartServiceImpl implements CartService {
    @Autowired
    OmsCartItemMapper omsCartItemMapper;
    @Autowired
    RedisUtil redisUtil;
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
        Map<String,String> map = new HashMap<>();
//        for (OmsCartItem cartItem : omsCartItems) {
//            cartItem.setTotalPrice(cartItem.getPrice().multiply(cartItem.getQuantity()));
//            map.put(cartItem.getProductSkuId(), JSON.toJSONString(cartItem));
//        }

        //TODO  保存到redis
    }

    @Override
    public List<OmsCartItem> cartList(String memberId) {
        List<OmsCartItem> omsCartItems = omsCartItemMapper.selectAllByMemberId(memberId);
        return omsCartItems;
    }

    @Override
    public void checkCart(OmsCartItem omsCartItem) {

        omsCartItemMapper.updateByMemberIdAndSkuId(omsCartItem.getMemberId(),omsCartItem.getProductSkuId(),omsCartItem.getIsChecked());

        // 缓存同步
        flushCartCache(omsCartItem.getMemberId());
    }
}
