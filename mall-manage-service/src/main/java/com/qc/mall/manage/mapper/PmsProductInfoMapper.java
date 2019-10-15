package com.qc.mall.manage.mapper;

import com.qc.mall.bean.PmsProductInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author qc
 * @date 2019/8/24
 * @description
 * @project mall
 */
@Mapper
public interface PmsProductInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(PmsProductInfo record);

    int insertSelective(PmsProductInfo record);

    PmsProductInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PmsProductInfo record);

    int updateByPrimaryKey(PmsProductInfo record);

    List<PmsProductInfo> selectByCataLogId(String catalog3Id);

    PmsProductInfo selectByProductName(String productName);
}
