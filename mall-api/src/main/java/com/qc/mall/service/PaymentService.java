package com.qc.mall.service;

import com.qc.mall.bean.PaymentInfo;
import org.mapstruct.Mapper;

import java.util.Map;

/**
 * @author qc
 * @date 2019/10/8
 * @description
 * @project mall
 */
@Mapper
public interface PaymentService {
    void savePaymentInfo(PaymentInfo paymentInfo);

    void updatePaymentByOrderSn(PaymentInfo paymentInfo);

    void sendDelayPaymentResultCheckQueue(String outTradeNo,int count);

    Map<String,Object> checkAlipayPayment(String out_trade_no);
}
