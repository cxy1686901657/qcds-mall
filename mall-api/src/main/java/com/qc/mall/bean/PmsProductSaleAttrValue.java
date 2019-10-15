package com.qc.mall.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @author qc
 * @date 2019/8/24
 * @description
 * @project mall
 */
@Data
public class PmsProductSaleAttrValue implements Serializable {
    private String id;

    private String productId;

    private String saleAttrId;

    private String saleAttrValueName;

    private  String isChecked;
}
