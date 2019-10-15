package com.qc.mall.bean;

import lombok.Data;

import java.beans.Transient;
import java.io.Serializable;
import java.util.List;

/**
 * @author qc
 * @date 2019/8/24
 * @description
 * @project mall
 */
@Data
public class PmsProductInfo implements Serializable {
    private String id;

    private String productName;

    private String description;

    private String catalog3Id;

    private String tmId;

    private List<PmsProductSaleAttr> spuSaleAttrList;

    private List<PmsProductImage> spuImageList;

}
