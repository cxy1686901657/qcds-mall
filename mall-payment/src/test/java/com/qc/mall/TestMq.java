package com.qc.mall;


import com.qc.mall.consts.MqQueueConst;
import com.qc.mall.payment.MallPaymentApplication;
import com.qc.mall.util.ActiveMQUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

/**
 * @author qc
 * @date 2019/10/9
 * @description
 * @project mall
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MallPaymentApplication.class)
public class TestMq {
    @Autowired
    ActiveMQUtil activeMQUtil;
    @Test
    public void test1(){
        HashMap<String, String> param = new HashMap<>();
        param.put("out_trade_no", "adadawdawcawa");
        activeMQUtil.sendTransactedMapMessage(MqQueueConst.PAYHMENT_SUCCESS_QUEUE, param);
//        activeMQUtil.sendDelayTransactedMapMessage(MqQueueConst.PAYMENT_CHECK_QUEUE, 10,param);
    }
}
