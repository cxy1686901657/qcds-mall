package com.qc.mall.bean;

import lombok.Data;

import java.beans.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author qc
 * @date 2019/8/26
 * @description
 * @project mall
 */
@Data
public class PmsSkuInfo implements Serializable {
    private String id;

    private String spuId;

    private BigDecimal price;

    private String skuName;

    private String skuDesc;

    private BigDecimal weight;

    private String tmId;

    private String catalog3Id;

    private String skuDefaultImg;

    List<PmsSkuImage> skuImageList;

    List<PmsSkuAttrValue> skuAttrValueList;

    List<PmsSkuSaleAttrValue> skuSaleAttrValueList;
}
