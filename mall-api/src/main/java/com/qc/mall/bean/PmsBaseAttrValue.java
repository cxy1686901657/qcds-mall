package com.qc.mall.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @author qc
 * @date 2019/8/22
 * @description
 * @project mall
 */
@Data
public class PmsBaseAttrValue implements Serializable {
    private String id;

    private String valueName;

    private String attrId;

    private String isEnabled;

}
