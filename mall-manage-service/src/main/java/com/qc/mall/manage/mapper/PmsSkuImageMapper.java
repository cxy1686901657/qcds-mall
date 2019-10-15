package com.qc.mall.manage.mapper;

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
public interface PmsSkuImageMapper {
    int deleteByPrimaryKey(String id);

    int insert(PmsSkuImage record);

    int insertSelective(PmsSkuImage record);

    PmsSkuImage selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PmsSkuImage record);

    int updateByPrimaryKey(PmsSkuImage record);

    List<PmsSkuImage> selectBySkuId(String skuId);
}
