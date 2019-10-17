package com.qc.mall.ware.mapper;

import com.qc.mall.bean.WareSku;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface WareSkuMapper {
    int deleteByPrimaryKey(String id);

    int insert(WareSku record);

    int insertSelective(WareSku record);

    WareSku selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WareSku record);

    int updateByPrimaryKey(WareSku record);

    Integer selectStockBySkuid(String skuid);

    List<WareSku> selectBySkuIdList(List<String> skuIdlist);

    List<WareSku> selectWareSkuAll();

    void deliveryStock(WareSku wareSku);

    List<WareSku> selectBySkuId(String skuId);

    int selectStockBySkuidForUpdate(WareSku wareSku);

    void incrStockLocked(WareSku wareSku);
}