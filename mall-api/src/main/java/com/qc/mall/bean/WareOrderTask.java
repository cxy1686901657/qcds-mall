package com.qc.mall.bean;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class WareOrderTask {
    private String id;

    private String orderId;

    private String consignee;

    private String consigneeTel;

    private String deliveryAddress;

    private String orderComment;

    private String paymentWay;

    private String taskStatus;

    private String orderBody;

    private String trackingNo;

    private Date createTime;

    private String wareId;

    private String taskComment;

    private List<WareOrderTaskDetail> details;
}