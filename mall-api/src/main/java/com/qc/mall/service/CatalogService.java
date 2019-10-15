package com.qc.mall.service;

import com.qc.mall.bean.PmsBaseCatalog1;
import com.qc.mall.bean.PmsBaseCatalog2;
import com.qc.mall.bean.PmsBaseCatalog3;

import java.util.List;

/**
 * @author qc
 * @date 2019/8/22
 * @description
 * @project mall
 */
public interface CatalogService {
    List<PmsBaseCatalog1> getCatalog1();

    List<PmsBaseCatalog2> getCatalog2(String catalog1Id);

    List<PmsBaseCatalog3> getCatalog3(String catalog2Id);
}
