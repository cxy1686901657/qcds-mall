package com.qc.mall.service;

import com.qc.mall.bean.PmsSkuInfo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author qc
 * @date 2019/8/26
 * @description
 * @project mall
 */
public interface SkuService {
    void saveSkuInfo(PmsSkuInfo pmsSkuInfo);

    PmsSkuInfo getSkuById(String skuId) throws InterruptedException;

    List<PmsSkuInfo> getSkuSaleAttrValueListBySpu(String spuId);

    List<PmsSkuInfo> getAll();

    boolean checkPrice(String productSkuId, BigDecimal price);
}
