package com.qc.mall.manage.mapper;

import com.qc.mall.bean.PmsSkuInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author qc
 * @date 2019/8/26
 * @description
 * @project mall
 */
@Mapper
public interface PmsSkuInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(PmsSkuInfo record);

    int insertSelective(PmsSkuInfo record);

    PmsSkuInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PmsSkuInfo record);

    int updateByPrimaryKey(PmsSkuInfo record);

    PmsSkuInfo selectBySkuName(String skuName);

    List<PmsSkuInfo> selectSkuSaleAttrValueListBySpu(String spuId);

    List<PmsSkuInfo> getAll();
}
