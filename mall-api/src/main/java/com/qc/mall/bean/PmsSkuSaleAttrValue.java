package com.qc.mall.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @author qc
 * @date 2019/8/26
 * @description
 * @project mall
 */
@Data
public class PmsSkuSaleAttrValue implements Serializable {
    private String id;

    private String skuId;

    private String saleAttrId;

    private String saleAttrValueId;

    private String saleAttrName;

    private String saleAttrValueName;
}
