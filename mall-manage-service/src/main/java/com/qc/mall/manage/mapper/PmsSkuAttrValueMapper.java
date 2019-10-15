package com.qc.mall.manage.mapper;

import com.qc.mall.bean.PmsSkuAttrValue;
import com.qc.mall.bean.PmsSkuImage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author qc
 * @date 2019/8/26
 * @description
 * @project mall
 */
@Mapper
public interface PmsSkuAttrValueMapper {
    int deleteByPrimaryKey(String id);

    int insert(PmsSkuAttrValue record);

    int insertSelective(PmsSkuAttrValue record);

    PmsSkuAttrValue selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PmsSkuAttrValue record);

    int updateByPrimaryKey(PmsSkuAttrValue record);

    List<PmsSkuAttrValue> selectBySkuId(String skuId);
}
