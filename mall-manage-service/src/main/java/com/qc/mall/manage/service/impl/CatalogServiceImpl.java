package com.qc.mall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qc.mall.bean.PmsBaseCatalog1;
import com.qc.mall.bean.PmsBaseCatalog2;
import com.qc.mall.bean.PmsBaseCatalog3;
import com.qc.mall.manage.mapper.PmsBaseCatalog1Mapper;
import com.qc.mall.manage.mapper.PmsBaseCatalog2Mapper;
import com.qc.mall.manage.mapper.PmsBaseCatalog3Mapper;
import com.qc.mall.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author qc
 * @date 2019/8/22
 * @description
 * @project mall
 */
@Service
@Component
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    PmsBaseCatalog1Mapper pmsBaseCatalog1Mapper;

    @Autowired
    PmsBaseCatalog2Mapper pmsBaseCatalog2Mapper;

    @Autowired
    PmsBaseCatalog3Mapper pmsBaseCatalog3Mapper;

    @Override
    public List<PmsBaseCatalog1> getCatalog1() {
        return pmsBaseCatalog1Mapper.getAll();
    }

    @Override
    public List<PmsBaseCatalog2> getCatalog2(String catalog1Id) {
        return pmsBaseCatalog2Mapper.getByCatalog1Id(catalog1Id);
    }

    @Override
    public List<PmsBaseCatalog3> getCatalog3(String catalog2Id) {

        return pmsBaseCatalog3Mapper.getByCatalog2Id(catalog2Id);
    }
}
