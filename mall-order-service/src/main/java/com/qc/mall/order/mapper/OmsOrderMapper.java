package com.qc.mall.order.mapper;

import com.qc.mall.bean.OmsOrder;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OmsOrderMapper {
    int deleteByPrimaryKey(String id);

    int insert(OmsOrder record);

    int insertSelective(OmsOrder record);

    OmsOrder selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OmsOrder record);

    int updateByPrimaryKey(OmsOrder record);

    OmsOrder selectByOrderSn(String orderSn);

    void updatePayStatusByOrderSn(OmsOrder omsOrder);
}