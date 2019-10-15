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
public class PmsSkuImage implements Serializable {
    private String id;

    private String skuId;

    private String imgName;

    private String imgUrl;

    private String spuImgId;

    private String isDefault;
}
