package com.qc.mall.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.qc.mall.bean.PmsSkuInfo;
import com.qc.mall.consts.MqQueueConst;
import com.qc.mall.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.MapMessage;

/**
 * @author qc
 * @date 2019/10/15
 * @description
 * @project qcds-mall
 */
@Component
@Slf4j
public class SearchServiceMqListener {
    @Autowired
    SearchService searchService;

    @JmsListener(destination = MqQueueConst.SKU_ADDTO_ES,containerFactory = "jmsQueueListener")
    public void consumeSKU_ADD_ES(MapMessage mapMessage) throws JMSException {
        String pmsSkuinfoJSON = mapMessage.getString("pmsSkuInfo");
        PmsSkuInfo pmsSkuInfo = JSON.parseObject(pmsSkuinfoJSON, PmsSkuInfo.class);
        try {
            searchService.sync(pmsSkuInfo);
            log.info("SKU_ADDTO_ES消费成功+入参{}",pmsSkuInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
