package com.qc.mall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qc.mall.bean.PmsBaseAttrInfo;
import com.qc.mall.bean.PmsBaseAttrValue;
import com.qc.mall.bean.PmsBaseSaleAttr;
import com.qc.mall.manage.mapper.PmsBaseAttrInfoMapper;
import com.qc.mall.manage.mapper.PmsBaseAttrValueMapper;
import com.qc.mall.manage.mapper.PmsBaseSaleAttrMapper;
import com.qc.mall.service.AttrService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;

/**
 * @author qc
 * @date 2019/8/22
 * @description
 * @project mall
 */
@Service
@Slf4j
@Component
public class AttrServiceImpl implements AttrService {
    @Autowired
    PmsBaseAttrInfoMapper pmsBaseAttrInfoMapper;
    @Autowired
    PmsBaseAttrValueMapper pmsBaseAttrValueMapper;

    @Autowired
    PmsBaseSaleAttrMapper pmsBaseSaleAttrMapper;

    @Override
    public List<PmsBaseAttrInfo> attrInfoList(String catalog3Id) {
        List<PmsBaseAttrInfo>  pmsBaseAttrInfos= pmsBaseAttrInfoMapper.getAttrInfoByCatalog3Id(catalog3Id);
        pmsBaseAttrInfos.stream().forEach(x->{
            x.setAttrValueList(pmsBaseAttrValueMapper.selectByAttrId(x.getId()));
        });
        return pmsBaseAttrInfos;
    }
    //TODO : 修改的bug
    @Override
    public String attrService(PmsBaseAttrInfo pmsBaseAttrInfo) {
        try{
            List<PmsBaseAttrValue> attrValueList = pmsBaseAttrInfo.getAttrValueList();

            PmsBaseAttrInfo  pmsBaseAttrInfo1=  pmsBaseAttrInfoMapper.selectByAttrInfoName(pmsBaseAttrInfo.getAttrName());
            if(pmsBaseAttrInfo1==null){
                pmsBaseAttrInfoMapper.insertSelective(pmsBaseAttrInfo);
                PmsBaseAttrInfo  pmsBaseAttrInfo2=pmsBaseAttrInfoMapper.selectByAttrInfoName(pmsBaseAttrInfo.getAttrName());
                attrValueList.stream().forEach(x->{
                    if(x.getValueName()!=null||!x.getValueName().equals("")){
                        x.setAttrId(pmsBaseAttrInfo2.getId());
                        pmsBaseAttrValueMapper.insertSelective(x);
                    }
                });
            }else{
                pmsBaseAttrInfoMapper.updateByPrimaryKeySelective(pmsBaseAttrInfo1);
                String attrId = pmsBaseAttrInfo.getId();
                pmsBaseAttrValueMapper.deleteByAttrId(attrId);
                attrValueList.stream().forEach(x->{
                    if(x.getValueName()!=null||!x.getValueName().equals("")){
                        x.setAttrId(pmsBaseAttrInfo1.getId());
                        pmsBaseAttrValueMapper.insertSelective(x);
                    }
                });
            }
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
        return "success";
    }

    @Override
    public List<PmsBaseAttrValue> getAttrValueList(String attrId) {
        List<PmsBaseAttrValue> list=  pmsBaseAttrValueMapper.selectByAttrId(attrId);
        return list;
    }

    @Override
    public List<PmsBaseSaleAttr> baseSaleAttrList() {
        return pmsBaseSaleAttrMapper.selectAll();
    }

    @Override
    public List<PmsBaseAttrInfo> getAttrValueListByValueId(HashSet<String> valueIds) {
        String valueIdStr = StringUtils.join(valueIds, ",");//41,45,46
        log.info("{}",valueIdStr);
        List<PmsBaseAttrInfo> pmsBaseAttrInfos = pmsBaseAttrInfoMapper.selectAttrValueListByValueId(valueIdStr);
        return pmsBaseAttrInfos;
    }


}
