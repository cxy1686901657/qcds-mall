package com.qc.mall.manage.mapper;

import com.qc.mall.bean.PmsBaseAttrInfo;
import com.qc.mall.bean.PmsBaseAttrValue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author qc
 * @date 2019/8/22
 * @description
 * @project mall
 */
@Mapper
public interface PmsBaseAttrInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(PmsBaseAttrInfo record);

    int insertSelective(PmsBaseAttrInfo record);

    PmsBaseAttrInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PmsBaseAttrInfo record);

    int updateByPrimaryKey(PmsBaseAttrInfo record);

    List<PmsBaseAttrInfo> getAttrInfoByCatalog3Id(String catalog3Id);

    PmsBaseAttrInfo selectByAttrInfoName(String attrName);

    List<PmsBaseAttrInfo> selectAttrValueListByValueId(@Param("valueIdStr") String valueIdStr);
}
