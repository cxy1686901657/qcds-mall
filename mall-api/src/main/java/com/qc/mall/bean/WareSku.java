package com.qc.mall.bean;

import lombok.Data;

@Data
public class WareSku {
    private String id;

    private String skuId;

    private String warehouseId;

    private Integer stock=0;

    private String stockName;

    private Integer stockLocked;

    private  String warehouseName;
}