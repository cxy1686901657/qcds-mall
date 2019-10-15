package com.qc.mall.service;

import com.qc.mall.bean.OmsOrder;

/**
 * @author qc
 * @date 2019/9/28
 * @description
 * @project mall
 */
public interface OrderService {
    String checkTradeCode(String memberId, String tradeCode);

    String genTradeCode(String memberId);

    void saveOrder(OmsOrder omsOrder);

    OmsOrder getOrderByOutTradeNo(String outTradeNo);

    void updateOrder(OmsOrder omsOrder);
}
