package com.qc.mall.manage.mapper;

import com.qc.mall.bean.PmsProductSaleAttrValue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PmsProductSaleAttrValueMapper {
    int deleteByPrimaryKey(String id);

    int insert(PmsProductSaleAttrValue record);

    int insertSelective(PmsProductSaleAttrValue record);

    PmsProductSaleAttrValue selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PmsProductSaleAttrValue record);

    int updateByPrimaryKey(PmsProductSaleAttrValue record);

    List<PmsProductSaleAttrValue> selectBySpuIdAndSaleAttrId(@Param(value = "productId") String productId,@Param(value = "saleAttrId")  String saleAttrId);
}