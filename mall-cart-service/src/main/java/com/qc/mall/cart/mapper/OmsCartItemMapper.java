package com.qc.mall.cart.mapper;

import com.qc.mall.bean.OmsCartItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author qc
 * @date 2019/9/17
 * @description
 * @project mall
 */
@Mapper
public interface OmsCartItemMapper {
    int deleteByPrimaryKey(String id);

    int insert(OmsCartItem record);

    int insertSelective(OmsCartItem record);

    OmsCartItem selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OmsCartItem record);

    int updateByPrimaryKey(OmsCartItem record);

    OmsCartItem selectOneByMemberIdAndSkuId(@Param("memberId") String memberId, @Param("skuid") String skuId);

    List<OmsCartItem> selectAllByMemberId(String memberId);

    void updateByMemberIdAndSkuId(@Param("memberId")String memberId, @Param("productSkuId") String productSkuId, @Param("isChecked") String isChecked);
}
