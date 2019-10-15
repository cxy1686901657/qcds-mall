package com.qc.mall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qc.mall.bean.PmsProductImage;
import com.qc.mall.bean.PmsProductInfo;
import com.qc.mall.bean.PmsProductSaleAttr;
import com.qc.mall.service.SpuService;
import com.qc.mall.utils.PmsUploadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author qc
 * @date 2019/8/24
 * @description
 * @project mall
 */
@RestController
@CrossOrigin
@Slf4j
public class SpuController {
    @Reference
    SpuService spuService;

    @RequestMapping("fileUpload")
    @ResponseBody
    public String fileUpload(@RequestParam("file") MultipartFile multipartFile){
        String imgUrl = null;
        try {
            imgUrl = PmsUploadUtil.uploadImage(multipartFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.error("imageurl:{}",imgUrl);
        return imgUrl;
    }

    @RequestMapping("spuList")
    @ResponseBody
    public List<PmsProductInfo> spuList(String catalog3Id){
        List<PmsProductInfo> pmsProductInfos = spuService.spuList(catalog3Id);
        return pmsProductInfos;
    }
    @RequestMapping("saveSpuInfo")
    @ResponseBody
    public String saveSpuInfo(@RequestBody PmsProductInfo pmsProductInfo){
        spuService.saveSpuInfo(pmsProductInfo);
        return "success";
    }


    @RequestMapping("spuImageList")
    @ResponseBody
    public List<PmsProductImage> spuImageList(String spuId){

        List<PmsProductImage> pmsProductImages = spuService.spuImageList(spuId);
        return pmsProductImages;
    }


    @RequestMapping("spuSaleAttrList")
    @ResponseBody
    public List<PmsProductSaleAttr> spuSaleAttrList(String spuId){

        List<PmsProductSaleAttr> pmsProductSaleAttrs = spuService.spuSaleAttrList(spuId);
        return pmsProductSaleAttrs;
    }

}
