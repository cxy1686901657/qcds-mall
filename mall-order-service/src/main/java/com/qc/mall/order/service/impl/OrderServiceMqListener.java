package com.qc.mall.order.service.impl;

import com.qc.mall.bean.OmsOrder;
import com.qc.mall.consts.MqQueueConst;
import com.qc.mall.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.MapMessage;

/**
 * @author qc
 * @date 2019/10/10
 * @description
 * @project mall
 */
@Component
@Slf4j
public class OrderServiceMqListener {
    @Autowired
    OrderService orderService;

    @JmsListener(destination = MqQueueConst.PAYHMENT_SUCCESS_QUEUE,containerFactory = "jmsQueueListener")
    public void consumePaymentResult(MapMessage mapMessage) throws JMSException {

        String out_trade_no = mapMessage.getString("out_trade_no");
        // 更新订单状态业务
        log.info("已消费订单号为：  {}",out_trade_no);
        OmsOrder omsOrder = new OmsOrder();
        omsOrder.setOrderSn(out_trade_no);
        orderService.updateOrder(omsOrder);

    }
}
