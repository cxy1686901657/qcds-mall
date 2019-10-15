package com.qc.mall.manage.mapper;

import com.qc.mall.bean.PmsSkuImage;
import com.qc.mall.bean.PmsSkuSaleAttrValue;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author qc
 * @date 2019/8/26
 * @description
 * @project mall
 */
@Mapper
public interface PmsSkuSaleAttrValueMapper {
    int deleteByPrimaryKey(String id);

    int insert(PmsSkuSaleAttrValue record);

    int insertSelective(PmsSkuSaleAttrValue record);

    PmsSkuSaleAttrValue selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PmsSkuSaleAttrValue record);

    int updateByPrimaryKey(PmsSkuSaleAttrValue record);

    List<PmsSkuSaleAttrValue> selectBySkuId(String skuId);
}
