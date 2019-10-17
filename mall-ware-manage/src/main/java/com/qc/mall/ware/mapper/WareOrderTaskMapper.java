package com.qc.mall.ware.mapper;

import com.qc.mall.bean.WareOrderTask;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface WareOrderTaskMapper {
    int deleteByPrimaryKey(String id);

    int insert(WareOrderTask record);

    int insertSelective(WareOrderTask record);

    WareOrderTask selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WareOrderTask record);

    int updateByPrimaryKey(WareOrderTask record);

    WareOrderTask selectByOrderid(String orderId);

    void updateByExampleSelective(WareOrderTask wareOrderTask, String orderId);

    List<WareOrderTask> selectAll();

    List<WareOrderTask> selectById(String id);
}