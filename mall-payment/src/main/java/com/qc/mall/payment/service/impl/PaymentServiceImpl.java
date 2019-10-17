package com.qc.mall.payment.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.qc.mall.bean.PaymentInfo;
import com.qc.mall.consts.MqQueueConst;
import com.qc.mall.payment.mapper.PaymentInfoMapper;
import com.qc.mall.service.PaymentService;
import com.qc.mall.util.ActiveMQUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @author qc
 * @date 2019/10/8
 * @description
 * @project mall
 */
@Service
@Component
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    PaymentInfoMapper paymentInfoMapper;

    @Autowired
    ActiveMQUtil activeMQUtil;

    @Autowired
    AlipayClient alipayClient;

    @Override
    public void savePaymentInfo(PaymentInfo paymentInfo) {
        paymentInfoMapper.insertSelective(paymentInfo);
    }
    @Transactional
    @Override
    public void updatePaymentByOrderSn(PaymentInfo paymentInfo) {

        // 幂等性检查
        PaymentInfo paymentInfoResult =paymentInfoMapper.selectByOrderSn(paymentInfo.getOrderSn());
        if(StringUtils.isNotBlank(paymentInfoResult.getPaymentStatus())&&paymentInfoResult.getPaymentStatus().equals("已支付")){
            return;
        }
        paymentInfoMapper.updateByOrderSnSelective(paymentInfo);
        HashMap<String, String> param = new HashMap<>();
        param.put("out_trade_no", paymentInfo.getOrderSn());
        //支付完成 发消息
        activeMQUtil.sendTransactedMapMessage(MqQueueConst.PAYHMENT_SUCCESS_QUEUE,param);
        log.info("支付成功发送消息成功PAYHMENT_SUCCESS_QUEUE+入参{}",param);
    }
    @Override
    public void sendDelayPaymentResultCheckQueue(String outTradeNo, int count) {
        HashMap<String, String> param = new HashMap<>();
        param.put("out_trade_no", outTradeNo);
        param.put("count", count+"");
        activeMQUtil.sendDelayTransactedMapMessage(MqQueueConst.PAYMENT_CHECK_QUEUE,param);
        log.info("延迟队列PAYMENT_CHECK_QUEUE发送信息成功+入参{}",param);
    }
    @Override
    public Map<String, Object> checkAlipayPayment(String out_trade_no) {
        Map<String,Object> resultMap = new HashMap<>();
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("out_trade_no",out_trade_no);
        request.setBizContent(JSON.toJSONString(requestMap));
        AlipayTradeQueryResponse response = null;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if(response.isSuccess()){
            log.info("有可能交易已创建，调用成功");
            resultMap.put("out_trade_no",response.getOutTradeNo());
            resultMap.put("trade_no",response.getTradeNo());
            resultMap.put("trade_status",response.getTradeStatus());
            resultMap.put("call_back_content",response.getMsg());
        } else {
            log.info("有可能交易未创建，调用失败");
        }
        return resultMap;
    }
}
