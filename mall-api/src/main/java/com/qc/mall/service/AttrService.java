package com.qc.mall.service;

import com.qc.mall.bean.PmsBaseAttrInfo;
import com.qc.mall.bean.PmsBaseAttrValue;
import com.qc.mall.bean.PmsBaseSaleAttr;

import java.util.HashSet;
import java.util.List;

/**
 * @author qc
 * @date 2019/8/22
 * @description
 * @project mall
 */

public interface AttrService {
    List<PmsBaseAttrInfo> attrInfoList(String catalog3Id);

    String attrService(PmsBaseAttrInfo pmsBaseAttrInfo);

    List<PmsBaseAttrValue> getAttrValueList(String attrId);

    List<PmsBaseSaleAttr> baseSaleAttrList();

    List<PmsBaseAttrInfo> getAttrValueListByValueId(HashSet<String> valueIds);
}
