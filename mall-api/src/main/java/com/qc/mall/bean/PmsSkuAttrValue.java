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
public class PmsSkuAttrValue implements Serializable {
    private String id;

    private String attrId;

    private String valueId;

    private String skuId;
}
