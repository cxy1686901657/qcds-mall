package com.qc.mall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qc.mall.bean.PmsBaseAttrInfo;
import com.qc.mall.bean.PmsBaseAttrValue;
import com.qc.mall.bean.PmsBaseSaleAttr;
import com.qc.mall.service.AttrService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author qc
 * @date 2019/8/22
 * @description
 * @project mall
 */
@CrossOrigin
@RestController
@Api(value = "商品属性接口", description = "商品属性接口")
public class AttrController {
    @Reference
    AttrService attrService;

    @RequestMapping("baseSaleAttrList")
    @ResponseBody
    @ApiOperation(value = "返回所有基本售卖属性", notes = "返回所有基本售卖属性")
    @ApiResponses({
            @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "error")
    })
    public List<PmsBaseSaleAttr> baseSaleAttrList(){
        List<PmsBaseSaleAttr> pmsBaseSaleAttrs = attrService.baseSaleAttrList();
        return pmsBaseSaleAttrs;
    }

    @RequestMapping("saveAttrInfo")
    @ResponseBody
    public String saveAttrInfo(@RequestBody PmsBaseAttrInfo pmsBaseAttrInfo){
        String msg=attrService.attrService(pmsBaseAttrInfo);
        return msg;
    }

    @RequestMapping("attrInfoList")
    @ResponseBody
    public List<PmsBaseAttrInfo> attrInfoList(String catalog3Id){
        List<PmsBaseAttrInfo> pmsBaseAttrInfos = attrService.attrInfoList(catalog3Id);
        return pmsBaseAttrInfos;
    }

    @RequestMapping("getAttrValueList")
    @ResponseBody
    public List<PmsBaseAttrValue> getAttrValueList(String attrId){

        List<PmsBaseAttrValue> pmsBaseAttrValues = attrService.getAttrValueList(attrId);
        return pmsBaseAttrValues;
    }


}
