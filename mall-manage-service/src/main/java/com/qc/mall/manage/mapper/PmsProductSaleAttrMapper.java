package com.qc.mall.manage.mapper;

import com.qc.mall.bean.PmsProductSaleAttr;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PmsProductSaleAttrMapper {
    int deleteByPrimaryKey(String id);

    int insert(PmsProductSaleAttr record);

    int insertSelective(PmsProductSaleAttr record);

    PmsProductSaleAttr selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PmsProductSaleAttr record);

    int updateByPrimaryKey(PmsProductSaleAttr record);

    List<PmsProductSaleAttr> selectBySpuId(String productId);

    List<PmsProductSaleAttr> selectBySpuIdAndSaleAttrId(@Param("spuId") String spuId, @Param("skuid") String skuid);
}