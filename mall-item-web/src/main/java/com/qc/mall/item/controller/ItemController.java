package com.qc.mall.item.controller;
import com.alibaba.fastjson.JSON;
import com.alibaba.dubbo.config.annotation.Reference;
import com.qc.mall.bean.PmsProductSaleAttr;
import com.qc.mall.bean.PmsSkuInfo;
import com.qc.mall.bean.PmsSkuSaleAttrValue;
import com.qc.mall.service.SkuService;
import com.qc.mall.service.SpuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author qc
 * @date 2019/8/26
 * @description
 * @project mall
 */
@Controller
@RequestMapping("/item")
@Slf4j
public class ItemController {
    @Reference
    SkuService skuService;

    @Reference
    SpuService spuService;

    @GetMapping("/{skuId}.html")
    public String item(@PathVariable String skuId, Model model) throws InterruptedException {
        log.info("skuid  ==  {}", skuId);
        PmsSkuInfo pmsSkuInfo = skuService.getSkuById(skuId);
        model.addAttribute("skuInfo", pmsSkuInfo);

        List<PmsProductSaleAttr> pmsProductSaleAttrList= spuService.spuSaleAttrListCheckBySku(pmsSkuInfo.getSpuId(),skuId);
        model.addAttribute("spuSaleAttrListCheckBySku", pmsProductSaleAttrList);

        List<PmsSkuInfo> pmsSkuInfos = skuService.getSkuSaleAttrValueListBySpu(pmsSkuInfo.getSpuId());
        Map<String, String> skuSaleAttrHash = new HashMap<>();
          pmsSkuInfos.stream().forEach(x->{
              String k = "";
              String v = x.getId();

              List<PmsSkuSaleAttrValue> skuSaleAttrValueList = x.getSkuSaleAttrValueList();
              for (PmsSkuSaleAttrValue pmsSkuSaleAttrValue : skuSaleAttrValueList) {
                  k += pmsSkuSaleAttrValue.getSaleAttrValueId() + "|";// "239|245"
              }
              skuSaleAttrHash.put(k,v);
          });

        String skuSaleAttrHashJsonStr = JSON.toJSONString(skuSaleAttrHash);
        model.addAttribute("skuSaleAttrHashJsonStr",skuSaleAttrHashJsonStr);

        return "item";
    }
}
