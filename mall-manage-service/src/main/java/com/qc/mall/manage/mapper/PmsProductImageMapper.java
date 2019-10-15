package com.qc.mall.manage.mapper;

import com.qc.mall.bean.PmsProductImage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PmsProductImageMapper {
    int deleteByPrimaryKey(String id);

    int insert(PmsProductImage record);

    int insertSelective(PmsProductImage record);

    PmsProductImage selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PmsProductImage record);

    int updateByPrimaryKey(PmsProductImage record);

    List<PmsProductImage> selectBySpuId(String spuId);
}