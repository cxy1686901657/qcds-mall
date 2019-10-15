package com.qc.mall.user.mapper;

import com.qc.mall.bean.UmsMember;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author qc
 * @date 2019/8/22
 * @description
 * @project mall
 */
@Mapper
public interface UmsMemberMapper {
    int deleteByPrimaryKey(String id);

    int insert(UmsMember record);

    int insertSelective(UmsMember record);

    UmsMember selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UmsMember record);

    int updateByPrimaryKey(UmsMember record);

    List<UmsMember> getAllUmsMember();

    List<UmsMember> select(UmsMember umsMember);

    UmsMember selectByUid(String sourceUid);

    void updateBySourceUid(UmsMember umsMember);
}
