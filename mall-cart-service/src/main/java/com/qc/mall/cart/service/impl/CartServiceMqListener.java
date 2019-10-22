package com.qc.mall.cart.service.impl;

import com.qc.mall.consts.MqQueueConst;
import com.qc.mall.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.MapMessage;

/**
 * @author qc
 * @date 2019/10/22
 * @description
 * @project qcds-mall
 */
@Component
public class CartServiceMqListener {

    @Autowired
    CartService cartService;

    @JmsListener(destination = MqQueueConst.UPDATE_CARTCACHE,containerFactory = "jmsQueueListener")
    public void consumeUPDATE_CARTCACHE(MapMessage mapMessage) throws JMSException {
        String memberId = mapMessage.getString("memberId");
        cartService.flushCartCache(memberId);
    }
    @JmsListener(destination = MqQueueConst.UPDATE_CART,containerFactory = "jmsQueueListener")
    public void consumeUPDATE_CART(MapMessage mapMessage) throws JMSException {
        String memberId = mapMessage.getString("memberId");
        cartService.flushCartCache(memberId);
    }
}
