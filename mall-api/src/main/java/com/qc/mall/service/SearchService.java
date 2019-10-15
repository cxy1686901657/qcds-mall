package com.qc.mall.service;

import com.qc.mall.bean.PmsSearchSkuInfo;
import com.qc.mall.bean.PmsSkuInfo;
import com.qc.mall.parm.PmsSearchParam;

import java.util.List;

/**
 * @author qc
 * @date 2019/9/12
 * @description
 * @project mall
 */
public interface SearchService {
    List<PmsSearchSkuInfo> list(PmsSearchParam pmsSearchParam);

    void sync(PmsSkuInfo pmsSkuInfo) throws Exception;
}
