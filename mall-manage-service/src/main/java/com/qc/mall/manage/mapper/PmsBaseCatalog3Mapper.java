package com.qc.mall.manage.mapper;

import com.qc.mall.bean.PmsBaseCatalog3;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author qc
 * @date 2019/8/22
 * @description
 * @project mall
 */
@Mapper
public interface PmsBaseCatalog3Mapper {
    int deleteByPrimaryKey(String id);

    int insert(PmsBaseCatalog3 record);

    int insertSelective(PmsBaseCatalog3 record);

    PmsBaseCatalog3 selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PmsBaseCatalog3 record);

    int updateByPrimaryKey(PmsBaseCatalog3 record);

    List<PmsBaseCatalog3> getByCatalog2Id(String catalog2Id);
}
