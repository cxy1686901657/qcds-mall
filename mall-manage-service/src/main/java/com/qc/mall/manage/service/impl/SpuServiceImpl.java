package com.qc.mall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qc.mall.bean.PmsProductImage;
import com.qc.mall.bean.PmsProductInfo;
import com.qc.mall.bean.PmsProductSaleAttr;
import com.qc.mall.bean.PmsProductSaleAttrValue;
import com.qc.mall.manage.mapper.PmsProductImageMapper;
import com.qc.mall.manage.mapper.PmsProductInfoMapper;
import com.qc.mall.manage.mapper.PmsProductSaleAttrMapper;
import com.qc.mall.manage.mapper.PmsProductSaleAttrValueMapper;
import com.qc.mall.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author qc
 * @date 2019/8/24
 * @description
 * @project mall
 */
@Service
@Component
public class SpuServiceImpl implements SpuService {
    @Autowired
    PmsProductInfoMapper pmsProductInfoMapper;

    @Autowired
    PmsProductImageMapper pmsProductImageMapper;

    @Autowired
    PmsProductSaleAttrMapper pmsProductSaleAttrMapper;

    @Autowired
    PmsProductSaleAttrValueMapper pmsProductSaleAttrValueMapper;

    @Override
    public List<PmsProductInfo> spuList(String catalog3Id) {
        List<PmsProductInfo> list =  pmsProductInfoMapper.selectByCataLogId(catalog3Id);
        return list;
    }

    @Override
    public void saveSpuInfo(PmsProductInfo pmsProductInfo) {
        pmsProductInfoMapper.insertSelective(pmsProductInfo);
        PmsProductInfo pmsProductInfo1 = pmsProductInfoMapper.selectByProductName(pmsProductInfo.getProductName());
        pmsProductInfo.getSpuImageList().stream().forEach(x->{
            x.setProductId(pmsProductInfo1.getId());
            pmsProductImageMapper.insertSelective(x);
        });
        pmsProductInfo.getSpuSaleAttrList().stream().forEach(x->{
            x.setProductId(pmsProductInfo1.getId());
            pmsProductSaleAttrMapper.insertSelective(x);
            x.getSpuSaleAttrValueList().stream().forEach(y->{
                y.setProductId(pmsProductInfo1.getId());
                pmsProductSaleAttrValueMapper.insertSelective(y);
            });
        });
    }

    @Override
    public List<PmsProductSaleAttr> spuSaleAttrList(String productId) {
        List<PmsProductSaleAttr> pmsProductSaleAttrList=pmsProductSaleAttrMapper.selectBySpuId(productId);
        pmsProductSaleAttrList.stream().forEach(x->{
            x.setSpuSaleAttrValueList(pmsProductSaleAttrValueMapper.selectBySpuIdAndSaleAttrId(productId,x.getSaleAttrId()));
        });
        return pmsProductSaleAttrList;
    }

    @Override
    public List<PmsProductImage> spuImageList(String spuId) {
        return pmsProductImageMapper.selectBySpuId(spuId);
    }

    @Override
    public List<PmsProductSaleAttr> spuSaleAttrListCheckBySku(String spuId,String skuid) {

        List<PmsProductSaleAttr> pmsProductSaleAttrList =  pmsProductSaleAttrMapper.selectBySpuIdAndSaleAttrId(spuId,skuid);


//        List<PmsProductSaleAttr> pmsProductSaleAttrList = pmsProductSaleAttrMapper.selectBySpuId(spuId);
//        pmsProductSaleAttrList.stream().forEach(x->{
//            List<PmsProductSaleAttrValue> pmsProductSaleAttrValues = pmsProductSaleAttrValueMapper.selectBySpuIdAndSaleAttrId(spuId, x.getSaleAttrId());
//            x.setSpuSaleAttrValueList(pmsProductSaleAttrValues);
//        });


        return pmsProductSaleAttrList;
    }
}
