package com.qc.mall.manage.mapper;

import com.qc.mall.bean.PmsBaseCatalog2;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author qc
 * @date 2019/8/22
 * @description
 * @project mall
 */
@Mapper
public interface PmsBaseCatalog2Mapper {
    int deleteByPrimaryKey(String id);

    int insert(PmsBaseCatalog2 record);

    int insertSelective(PmsBaseCatalog2 record);

    PmsBaseCatalog2 selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PmsBaseCatalog2 record);

    int updateByPrimaryKey(PmsBaseCatalog2 record);

    List<PmsBaseCatalog2> getByCatalog1Id(String catalog1Id);
}
