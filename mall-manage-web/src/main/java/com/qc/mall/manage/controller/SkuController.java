package com.qc.mall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qc.mall.bean.PmsSkuInfo;
import com.qc.mall.service.SkuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @author qc
 * @date 2019/8/26
 * @description
 * @project mall
 */
@RestController
@CrossOrigin
public class SkuController {
    @Reference
    SkuService skuService;
    @RequestMapping("saveSkuInfo")
    @ResponseBody
    public String saveSkuInfo(@RequestBody PmsSkuInfo pmsSkuInfo){
        // 处理默认图片
        String skuDefaultImg = pmsSkuInfo.getSkuDefaultImg();
        if(StringUtils.isBlank(skuDefaultImg)){
            if(pmsSkuInfo.getSkuImageList().size()!=0){
                pmsSkuInfo.setSkuDefaultImg(pmsSkuInfo.getSkuImageList().get(0).getImgUrl());
            }
        }
        skuService.saveSkuInfo(pmsSkuInfo);
        return "success";
    }

}
