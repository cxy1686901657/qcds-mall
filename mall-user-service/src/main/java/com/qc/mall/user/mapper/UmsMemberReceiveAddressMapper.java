package com.qc.mall.user.mapper;

import com.qc.mall.bean.UmsMemberReceiveAddress;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author qc
 * @date 2019/8/22
 * @description
 * @project mall
 */
@Mapper
public interface UmsMemberReceiveAddressMapper  {
    int deleteByPrimaryKey(String id);

    int insert(UmsMemberReceiveAddress record);

    int insertSelective(UmsMemberReceiveAddress record);

    UmsMemberReceiveAddress selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UmsMemberReceiveAddress record);

    int updateByPrimaryKey(UmsMemberReceiveAddress record);

    List<UmsMemberReceiveAddress> selectByMemberId(String usermemberId);
}
