package com.qc.mall.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author qc
 * @date 2019/8/22
 * @description
 * @project mall
 */
@Data
public class PmsBaseAttrInfo implements Serializable {
    private String id;

    private String attrName;

    private String catalog3Id;

    private String isEnabled;

    List<PmsBaseAttrValue> attrValueList;
}
