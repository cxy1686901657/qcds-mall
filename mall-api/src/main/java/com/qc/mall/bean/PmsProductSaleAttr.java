package com.qc.mall.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author qc
 * @date 2019/8/24
 * @description
 * @project mall
 */
@Data
public class PmsProductSaleAttr implements Serializable {
    private String id;

    private String productId;

    private String saleAttrId;

    private String saleAttrName;

    List<PmsProductSaleAttrValue> spuSaleAttrValueList;
}
