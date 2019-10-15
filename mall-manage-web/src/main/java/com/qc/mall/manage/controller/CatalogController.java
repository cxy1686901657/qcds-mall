package com.qc.mall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qc.mall.bean.PmsBaseCatalog1;
import com.qc.mall.bean.PmsBaseCatalog2;
import com.qc.mall.bean.PmsBaseCatalog3;
import com.qc.mall.service.CatalogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author qc
 * @date 2019/8/22
 * @description
 * @project mall
 */
@RestController
@CrossOrigin
public class CatalogController {

    @Reference
    CatalogService catalogService;

    @RequestMapping("getCatalog1")
    @ResponseBody
    public List<PmsBaseCatalog1> getCatalog1(){

        List<PmsBaseCatalog1> catalog1s = catalogService.getCatalog1();
        return catalog1s;
    }
    @RequestMapping("getCatalog2")
    @ResponseBody
    public List<PmsBaseCatalog2> getCatalog2(String catalog1Id){

        List<PmsBaseCatalog2> catalog2s = catalogService.getCatalog2(catalog1Id);
        return catalog2s;
    }

    @RequestMapping("getCatalog3")
    @ResponseBody
    public List<PmsBaseCatalog3> getCatalog3(String catalog2Id){

        List<PmsBaseCatalog3> catalog3s = catalogService.getCatalog3(catalog2Id);
        return catalog3s;
    }


}
