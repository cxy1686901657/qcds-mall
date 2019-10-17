package com.qc.mall.payment.mapper;

import com.qc.mall.bean.PaymentInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author qc
 * @date 2019/10/8
 * @description
 * @project mall
 */
@Mapper
public interface PaymentInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(PaymentInfo record);

    int insertSelective(PaymentInfo record);

    PaymentInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PaymentInfo record);

    int updateByPrimaryKey(PaymentInfo record);

    void updateByOrderSnSelective(PaymentInfo paymentInfo);

    PaymentInfo selectByOrderSn(String orderSn);
}
