package com.qc.mall.ware.mapper;

import com.qc.mall.bean.WareOrderTaskDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface WareOrderTaskDetailMapper {
    int deleteByPrimaryKey(String id);

    int insert(WareOrderTaskDetail record);

    int insertSelective(WareOrderTaskDetail record);

    WareOrderTaskDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WareOrderTaskDetail record);

    int updateByPrimaryKey(WareOrderTaskDetail record);

    List<WareOrderTaskDetail> selectByTaskId(String taskId);
}