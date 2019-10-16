package com.qc.mall.order.mapper;

import com.qc.mall.bean.OmsOrderItem;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OmsOrderItemMapper {
    int deleteByPrimaryKey(String id);

    int insert(OmsOrderItem record);

    int insertSelective(OmsOrderItem record);

    OmsOrderItem selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OmsOrderItem record);

    int updateByPrimaryKey(OmsOrderItem record);
}