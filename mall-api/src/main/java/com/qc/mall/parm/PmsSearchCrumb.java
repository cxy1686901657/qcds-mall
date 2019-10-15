package com.qc.mall.parm;

import lombok.Data;

import java.io.Serializable;

/**
 * @author qc
 * @date 2019/9/16
 * @description
 * @project mall
 */
@Data
public class PmsSearchCrumb implements Serializable {

    private String valueId;

    private String valueName;

    private String urlParam;
}

