package com.qc.mall.manage.mapper;

import com.qc.mall.bean.PmsBaseSaleAttr;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author qc
 * @date 2019/8/24
 * @description
 * @project mall
 */
@Mapper
public interface PmsBaseSaleAttrMapper {
    int deleteByPrimaryKey(String id);

    int insert(PmsBaseSaleAttr record);

    int insertSelective(PmsBaseSaleAttr record);

    PmsBaseSaleAttr selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PmsBaseSaleAttr record);

    int updateByPrimaryKey(PmsBaseSaleAttr record);

    List<PmsBaseSaleAttr> selectAll();
}
