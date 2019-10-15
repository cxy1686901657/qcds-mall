package com.qc.mall.manage.mapper;

import com.qc.mall.bean.PmsBaseAttrValue;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author qc
 * @date 2019/8/22
 * @description
 * @project mall
 */
@Mapper
public interface PmsBaseAttrValueMapper {
    int deleteByPrimaryKey(String id);

    int insert(PmsBaseAttrValue record);

    int insertSelective(PmsBaseAttrValue record);

    PmsBaseAttrValue selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PmsBaseAttrValue record);

    int updateByPrimaryKey(PmsBaseAttrValue record);

    List<PmsBaseAttrValue> selectByAttrId(String attrId);

    void deleteByAttrId(String attrId);
}
