package com.qc.mall.bean;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author qc
 * @date 2019/9/10
 * @description
 * @project mall
 */
@Data
public class PmsSearchSkuInfo implements Serializable {
    private String id;
    private String skuName;
    private String skuDesc;
    private String catalog3Id;
    private BigDecimal price;
    private String skuDefaultImg;
    private double hotScore;
    private String productId;
    List<PmsSkuAttrValue> skuAttrValueList;

}
