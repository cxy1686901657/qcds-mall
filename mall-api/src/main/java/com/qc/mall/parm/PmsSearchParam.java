package com.qc.mall.parm;

import lombok.Data;

import java.io.Serializable;
/**
 * @author qc
 * @date 2019/9/12
 * @description
 * @project mall
 */
@Data
public class PmsSearchParam implements Serializable {

    private String catalog3Id;

    private String keyword;

    private String[] valueId;
}
