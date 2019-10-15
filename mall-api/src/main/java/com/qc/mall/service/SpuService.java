package com.qc.mall.service;

import com.qc.mall.bean.PmsProductImage;
import com.qc.mall.bean.PmsProductInfo;
import com.qc.mall.bean.PmsProductSaleAttr;

import java.util.List;

/**
 * @author qc
 * @date 2019/8/24
 * @description
 * @project mall
 */
public interface SpuService {
    List<PmsProductInfo> spuList(String catalog3Id);

    void saveSpuInfo(PmsProductInfo pmsProductInfo);

    List<PmsProductSaleAttr> spuSaleAttrList(String spuId);

    List<PmsProductImage> spuImageList(String spuId);

    List<PmsProductSaleAttr> spuSaleAttrListCheckBySku(String spuId,String skuid);
}
