package com.qc.mall.manage.mapper;

import com.qc.mall.bean.PmsBaseCatalog1;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author qc
 * @date 2019/8/22
 * @description
 * @project mall
 */
@Mapper
public interface PmsBaseCatalog1Mapper {
    int deleteByPrimaryKey(String id);

    int insert(PmsBaseCatalog1 record);

    int insertSelective(PmsBaseCatalog1 record);

    PmsBaseCatalog1 selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PmsBaseCatalog1 record);

    int updateByPrimaryKey(PmsBaseCatalog1 record);

    List<PmsBaseCatalog1> getAll();
}
