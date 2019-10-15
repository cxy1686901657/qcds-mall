package com.qc.mall.bean;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author qc
 * @date 2019/10/8
 * @description
 * @project mall
 */
@Data
public class PaymentInfo implements Serializable {
    private String id;

    private String orderSn;

    private String orderId;

    private String alipayTradeNo;

    private BigDecimal totalAmount;

    private String subject;

    private String paymentStatus;

    private Date createTime;

    private Date confirmTime;

    private String callbackContent;

    private Date callbackTime;

}
