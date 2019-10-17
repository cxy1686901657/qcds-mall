package com.qc.mall.ware.mapper;

import com.qc.mall.bean.WareInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface WareInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(WareInfo record);

    int insertSelective(WareInfo record);

    WareInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WareInfo record);

    int updateByPrimaryKey(WareInfo record);

    List<WareInfo> selectWareInfoBySkuid(String skuid);

    List<WareInfo> selectAll();
}